package com.example.loanapp.exception;

public class CustomerSaveException extends Exception {
    public CustomerSaveException(String message, String eMessage) {
        super(message);
    }
}
