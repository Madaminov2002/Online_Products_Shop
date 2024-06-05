package org.example.online_products_shop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "Authentication API",
        description = "Uses in authentication"
)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @Operation(summary = "User signup", description = "Uses in registration and token generation")
    public ResponseEntity<JwtResponse> signup(@RequestBody SignupDto signupDto) {
        return ResponseEntity.ok(authService.signUp(signupDto));
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Uses for login account and token generation")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('SUPER ADMIN')")
    @Operation(summary = "Update user", description = "Uses for update users and only for SUPER ADMIN")
    public ResponseEntity<User> updateUser(@RequestBody UserUpdateDto updateDto) {
        return ResponseEntity.ok(authService.updateUser(updateDto));
    }

    @GetMapping("/checking_user/user-id/{id}")
    @Operation(summary = "Checking user", description = "Uses for checking verification user")
    public ResponseEntity<User> checkingUser(@RequestParam("password") String password, @PathVariable("id") Long userId) {
        return ResponseEntity.ok(authService.checkingForVerification(password, userId));
    }

    @GetMapping("/check_ForgotPassword")
    @Operation(summary = "Forgot password", description = "Uses for forgot password and user sending email to server ")
    public ResponseEntity<ForgotPassword> emailSentByUser(@RequestParam("email") String email) {
        return ResponseEntity.ok(authService.generatePasswordAndSaveToForgotPassword(email));
    }

    @PostMapping("/check_ForgotPassword")
    @Operation(summary = "Check Forgot password", description = "Uses for checking forgot password and sends the user generated password")
    private ResponseEntity<ForgotPasswordProjection> checkForgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) {
        return ResponseEntity.ok(authService.checkSentPassword(forgotPasswordDto.getPassword(), forgotPasswordDto.getEmail()));
    }

    @PostMapping("/change-password")
    @Operation(summary = "Change password", description = "Uses for changing account password")
    public ResponseEntity<UserDtoProjection> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        return ResponseEntity.ok(authService.checkUserEnabledFromForgotPassword(changePasswordDto));
    }

}
