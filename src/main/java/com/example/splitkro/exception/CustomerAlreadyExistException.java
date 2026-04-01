package com.example.splitkro.exception;

public class CustomerAlreadyExistException extends RuntimeException {
      public  CustomerAlreadyExistException(String message){
          super(message);
      }
}
