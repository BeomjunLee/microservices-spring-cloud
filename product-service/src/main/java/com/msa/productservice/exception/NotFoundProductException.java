package com.msa.productservice.exception;

public class NotFoundProductException extends RuntimeException{
    public NotFoundProductException(String message) {
        super(message);
    }
}
