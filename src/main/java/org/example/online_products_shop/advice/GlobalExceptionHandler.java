package org.example.online_products_shop.advice;

import jakarta.servlet.http.HttpServletResponse;
import org.example.online_products_shop.dto.ErrorResponseDto;
import org.example.online_products_shop.exception.EmailAlreadyExistsException;
import org.example.online_products_shop.exception.EmailNotFoundException;
import org.example.online_products_shop.exception.PasswordIncorrectException;
import org.example.online_products_shop.exception.UserNotEnableForChangingPasswordException;
import org.example.online_products_shop.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorResponseDto> userNotFound(UserNotFoundException exception) {
        return ResponseEntity.ok(
                ErrorResponseDto.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.NOT_FOUND)
                        .code(HttpServletResponse.SC_NOT_FOUND)
                        .build()
        );
    }

    @ExceptionHandler(PasswordIncorrectException.class)
    public ResponseEntity<ErrorResponseDto> incorrectPassword(PasswordIncorrectException exception) {
        return ResponseEntity.ok(
                ErrorResponseDto.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> emailAlreadyExists(EmailAlreadyExistsException exception) {
        return ResponseEntity.ok(
                ErrorResponseDto.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.CONFLICT)
                        .code(HttpServletResponse.SC_CONFLICT)
                        .build()
        );
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> emailNotFound(EmailNotFoundException exception) {
        return ResponseEntity.ok(
                ErrorResponseDto.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler(UserNotEnableForChangingPasswordException.class)
    public ResponseEntity<ErrorResponseDto> userNotEnabled(UserNotEnableForChangingPasswordException exception) {
        return ResponseEntity.ok(
                ErrorResponseDto.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .build()
        );
    }
}
