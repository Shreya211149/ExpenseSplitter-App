package com.example.splitkro.exception;

public class PayerNotFoundException extends RuntimeException{
    public PayerNotFoundException(String message){
        super(message);
    }
}
