package com.poslifayproject.poslifay.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class ErrorResponse {
    private HttpStatus status;
    private String message;
    private LocalDateTime timestamp;

    // Yapıcı Metotlar (Constructors)
    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(HttpStatus status, String message) {
        this(); // Parametresiz yapıcıyı çağırarak zaman damgasını ayarla
        this.status = status;
        this.message = message;
    }

    public ErrorResponse(HttpStatus status, String message, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getter ve Setter Metotları
    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // İsteğe Bağlı: Daha iyi kullanılabilirlik için equals, hashCode, toString
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ErrorResponse that = (ErrorResponse) o;
        return status == that.status && Objects.equals(message, that.message)
                && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, message, timestamp);
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "status=" + status +
                ", message=\"" + message + "\"" +
                ", timestamp=" + timestamp +
                '}';
    }
}
