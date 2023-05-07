package com.project.vinylata.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class BaseExceptionHandler {
//    @ResponseBody
//    @ExceptionHandler(value = UserAlreadyExistsException.class)
//    public final ResponseEntity<?> handleException(UserAlreadyExistsException exception) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
//    }
    @ResponseBody
    @ExceptionHandler(value = ProductAlreadyExistsException.class)
    public final ResponseEntity<?> handleException(ProductAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
    @ResponseBody
    @ExceptionHandler(value = ProductNotExistException.class)
    public final ResponseEntity<?> handleException(ProductNotExistException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
    @ResponseBody
    @ExceptionHandler(value = ProductNotFoundException.class)
    public final ResponseEntity<?> handleExeption(ProductNotFoundException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = CartItemNotExistException.class)
    public final ResponseEntity<?> handleException(CartItemNotExistException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = VoucherNotFoundException.class)
    public final ResponseEntity<?> handleException(VoucherNotFoundException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = UserNotFoundException.class)
    public final ResponseEntity<?> handleException(UserNotFoundException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public final ResponseEntity<?> handleException(UserAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = JWTNotValidateException.class)
    public final ResponseEntity<?> handleException(JWTNotValidateException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = EmailMessageException.class)
    public final ResponseEntity<?> handleException(EmailMessageException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = JwtAuthenticationException.class)
    public final ResponseEntity<?> handleException(JwtAuthenticationException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

}
