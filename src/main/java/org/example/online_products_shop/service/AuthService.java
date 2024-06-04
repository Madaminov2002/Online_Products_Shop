package org.example.online_products_shop.service;

import io.jsonwebtoken.Claims;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.online_products_shop.domain.ForgotPassword;
import org.example.online_products_shop.domain.Role;
import org.example.online_products_shop.domain.User;
import org.example.online_products_shop.domain.Verification;
import org.example.online_products_shop.dto.ChangePasswordDto;
import org.example.online_products_shop.dto.LoginDto;
import org.example.online_products_shop.dto.SendMailDto;
import org.example.online_products_shop.dto.SignupDto;
import org.example.online_products_shop.exception.EmailAlreadyExistsException;
import org.example.online_products_shop.exception.EmailNotFoundException;
import org.example.online_products_shop.exception.PasswordIncorrectException;
import org.example.online_products_shop.exception.UserNotEnableForChangingPasswordException;
import org.example.online_products_shop.exception.UserNotFoundException;
import org.example.online_products_shop.jwt.JwtProvider;
import org.example.online_products_shop.jwt.JwtResponse;
import org.example.online_products_shop.projection.ForgotPasswordProjection;
import org.example.online_products_shop.projection.UserDtoProjection;
import org.example.online_products_shop.repository.ForgotPasswordRepository;
import org.example.online_products_shop.repository.UserRepository;
import org.example.online_products_shop.repository.VerificationRepository;
import org.example.online_products_shop.updatedto.UserUpdateDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final VerificationRepository verificationRepository;
    private final JavaMailSender mailSender;
    private final ForgotPasswordRepository forgotPasswordRepository;

    /**
     * Default role User for everyone
     * Only SUPER_ADMIN adds  simple admins
     *
     * @param signupDto
     * @return User entity
     */
    public User dtoToEntity(SignupDto signupDto) {
        return User.builder()
                .username(signupDto.getUsername())
                .email(signupDto.getEmail())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .roles(List.of(Role.builder().id(4L).name("USER").build()))
                .build();
    }

    @SneakyThrows
    public JwtResponse signUp(SignupDto signupDto) {
        if (userRepository.existsByEmail(signupDto.getEmail())) {
            throw new EmailAlreadyExistsException(signupDto.getEmail());
        }

        User user = dtoToEntity(signupDto);

        User savedUser = userRepository.save(user);

        String token = jwtProvider.generate(savedUser);

        saveToVerification(token);

        return new JwtResponse(user.getId(), token, signupDto.getLocation());
    }

    /**
     * Saves the password created in the jwtProvider to a table and sends it to the user's email
     * sends temporarily only for mailtrap. (this for testing)
     *
     * @param token
     */
    @SneakyThrows
    public void saveToVerification(String token) {
        Claims claims = jwtProvider.parse(token);
        String password = claims.get("password", String.class);
        sendMail(password);
        verificationRepository.save(
                Verification.builder()
                        .email(claims.getSubject())
                        .password(password)
                        .expiryTime(LocalTime.now().plusHours(2L))
                        .build()
        );
    }

    public User checkingForVerification(String password, Long userId) {
        User user = userRepository.findById(userId).get();
        Verification verification = verificationRepository.findByEmail(user.getEmail());
        if (password.equals(verification.getPassword()) && verification.getExpiryTime().isAfter(LocalTime.now())) {
            return user;
        }
        throw new PasswordIncorrectException(password);
    }


    public JwtResponse login(LoginDto loginDto) {

        User user = userRepository.findByEmail(loginDto.getEmail());

        if (!userRepository.existsByEmail(loginDto.getEmail())) {
            throw new EmailNotFoundException(loginDto.getEmail());
        }

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new PasswordIncorrectException(loginDto.getPassword());
        }

        String token = jwtProvider.generate(user);

        return new JwtResponse(user.getId(), token, loginDto.getLocation());
    }

    public User updateUser(UserUpdateDto updateDto) {

        User user = userRepository.findById(updateDto.getId()).orElse(null);

        if (user == null) {
            throw new UserNotFoundException(String.valueOf(updateDto.getId()));
        }

        if (updateDto.getUsername() != null) {
            user.setUsername(updateDto.getUsername());
        }
        if (updateDto.getEmail() != null) {
            user.setEmail(updateDto.getEmail());
        }
        if (updateDto.getPassword() != null) {
            user.setPassword(updateDto.getPassword());
        }

        return userRepository.save(user);
    }

    /**
     * This method  temporarily only for mailtrap! (this for testing)
     *
     * @param password
     */
    @Async
    public void sendMail(String password) {
        SendMailDto dto = SendMailDto.builder()
                .to("admin@example.com")
                .content(password)
                .subject("Password").build();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setText(dto.getContent());
        mailMessage.setSubject(dto.getSubject());
        mailMessage.setSentDate(new Date());
        mailMessage.setTo(dto.getTo());
        mailSender.send(mailMessage);
    }

    public ForgotPassword generatePasswordAndSaveToForgotPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException(email);
        }
        int password = new Random().nextInt(100000, 1000000);

        sendMail(String.valueOf(password));

        return forgotPasswordRepository.save(
                ForgotPassword.builder()
                        .email(email)
                        .password(String.valueOf(password))
                        .build()
        );
    }

    public ForgotPasswordProjection checkSentPassword(String password, String email) {
        var forgotPasswordByEmailAndPassword = forgotPasswordRepository.findForgotPasswordByEmailAndPassword(email, password);
        if (forgotPasswordByEmailAndPassword == null) {
            throw new PasswordIncorrectException(password);
        }
        forgotPasswordRepository.updateEnabledToTrue(email);
        return forgotPasswordByEmailAndPassword;
    }

    public UserDtoProjection checkUserEnabledFromForgotPassword(ChangePasswordDto dto) {
        Boolean enabled = forgotPasswordRepository.checkByEmailEnabled(dto.getEmail());
        if (enabled == null) {
            throw new UserNotEnableForChangingPasswordException(dto.getEmail());
        }
        userRepository.changePassword(passwordEncoder.encode(dto.getNewPassword()), dto.getEmail());

        return userRepository.getChangedPasswordUser(dto.getEmail());
    }

}
