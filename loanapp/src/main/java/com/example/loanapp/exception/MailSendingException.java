package com.example.loanapp.exception;

public class MailSendingException extends Exception {
    public MailSendingException(String errorMessage, Exception e) {
        super(errorMessage,e);
    }
}
