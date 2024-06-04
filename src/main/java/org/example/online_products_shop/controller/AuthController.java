package org.example.online_products_shop.controller;

import lombok.RequiredArgsConstructor;
import org.example.online_products_shop.domain.ForgotPassword;
import org.example.online_products_shop.domain.User;
import org.example.online_products_shop.dto.ChangePasswordDto;
import org.example.online_products_shop.dto.ForgotPasswordDto;
import org.example.online_products_shop.dto.LoginDto;
import org.example.online_products_shop.dto.SignupDto;
import org.example.online_products_shop.jwt.JwtResponse;
import org.example.online_products_shop.projection.ForgotPasswordProjection;
import org.example.online_products_shop.projection.UserDtoProjection;
import org.example.online_products_shop.service.AuthService;
import org.example.online_products_shop.updatedto.UserUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> signup(@RequestBody SignupDto signupDto) {
        return ResponseEntity.ok(authService.signUp(signupDto));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<User> updateUser(@RequestBody UserUpdateDto updateDto) {
        return ResponseEntity.ok(authService.updateUser(updateDto));
    }

    @GetMapping("/checking_user/user-id/{id}")
    public ResponseEntity<User> checkingUser(@RequestParam("password") String password, @PathVariable("id") Long userId) {
        return ResponseEntity.ok(authService.checkingForVerification(password, userId));
    }

    @GetMapping("/check_ForgotPassword")
    public ResponseEntity<ForgotPassword> emailSentByUser(@RequestParam("email") String email) {
        return ResponseEntity.ok(authService.generatePasswordAndSaveToForgotPassword(email));
    }

    @PostMapping("/check_ForgotPassword")
    private ResponseEntity<ForgotPasswordProjection> checkForgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) {
        return ResponseEntity.ok(authService.checkSentPassword(forgotPasswordDto.getPassword(), forgotPasswordDto.getEmail()));
    }

    @PostMapping("/change-password")
    public ResponseEntity<UserDtoProjection> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        return ResponseEntity.ok(authService.checkUserEnabledFromForgotPassword(changePasswordDto));
    }

}
