package com.haptiq.secureBlogTest.globalResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;


@ControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        // to handle String return type manually
        if (returnType.getParameterType().equals(String.class)) {
            try {
                ApiResponse<Object> wrapper = new ApiResponse<>(
                        true,
                        HttpStatus.OK,
                        "Request processed successfully",
                        body
                );
                return objectMapper.writeValueAsString(wrapper); // serialize manually
            } catch (Exception e) {
                throw new RuntimeException("Failed to write response as string", e);
            }
        }

        // Avoid double-wrapping
        if (body instanceof ApiResponse<?>) {
            return body;
        }

        // Wrap all other types
        return new ApiResponse<>(
                true,
                HttpStatus.OK,
                "Request processed successfully",
                body
        );
    }
}
