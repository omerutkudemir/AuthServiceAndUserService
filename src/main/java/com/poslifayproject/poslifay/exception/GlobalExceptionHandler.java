package com.poslifayproject.poslifay.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Logger tanımı
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
            WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException ex,
            WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyUsedException(EmailAlreadyUsedException ex,
            WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationException(AuthorizationException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(TokenValidationException.class)
    public ResponseEntity<ErrorResponse> handleTokenValidationException(TokenValidationException ex,
            WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(OperationFailedException.class)
    public ResponseEntity<ErrorResponse> handleOperationFailedException(OperationFailedException ex,
            WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(),
                LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Diğer RuntimeException'lar için genel İstisna işleyici
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorResponse> handleGenericException(GenericException ex, WebRequest request) {
        // Genel istisnayı logla
        logger.error("Beklenmeyen bir hata oluştu: ", ex);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Beklenmeyen bir hata oluştu. Lütfen daha sonra tekrar deneyin.", LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Açıkça ele alınmayan diğer tüm istisnalar için yedek işleyici
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        // İstisnayı logla
        logger.error("Beklenmeyen bir hata oluştu: ", ex);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Dahili bir sunucu hatası oluştu.", LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
