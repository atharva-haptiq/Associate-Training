package com.haptiq.secureBlogTest.globalResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class ApiResponse<T> {

    private boolean success;
    private HttpStatus httpStatus;
    private String message;
    private T data;

    public ApiResponse(boolean success, HttpStatus httpStatus, String message, T data) {
        this.success = success;
        this.httpStatus = httpStatus;
        this.message = message;
        this.data = data;
    }

    public ApiResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
