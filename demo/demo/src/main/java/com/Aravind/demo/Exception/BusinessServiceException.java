package com.Aravind.demo.Exception;

public class BusinessServiceException extends RuntimeException {
    public BusinessServiceException(String message, Throwable cause) {
        super(message, cause);

        }
    }
