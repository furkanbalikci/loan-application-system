package com.example.loanapp.service;

import com.example.loanapp.exception.MailSendingException;
import com.example.loanapp.model.Customer;
import com.example.loanapp.model.LoanApp;
import com.example.loanapp.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

//EmailService.java

/**
 * This class provides an implementation for sending emails
 */
@Service
public class EmailService {

    // Autowiring the JavaMailSender to send emails
    @Autowired
    private JavaMailSender mailSender;

    // Logger to log error messages while sending email
    Logger logger = LoggerFactory.getLogger(EmailService.class);

    /**
     * Method to send an email using SimpleMailMessage
     * @param newMessage object of Message which contains the message details
     */
    public void sendMail(Message newMessage) throws MailSendingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(newMessage.getTo());
        message.setSubject(newMessage.getSubject());
        message.setText(newMessage.getBody());
        try {
            mailSender.send(message);
        } catch (Exception e) {
            // logging the error while sending email
            String errorMessage = "Error while sending email to " + message.getTo() + "." + e.getMessage();
            logger.error(errorMessage, e);
            throw new MailSendingException(errorMessage, e);
        }
    }


    /**
     * Method to create a new message object with given details
     * @param customer object of Customer containing customer details
     * @param loanApp object of LoanApp containing loan application details
     * @param subject the subject of the message
     * @return Message object containing message details
     * @throws IllegalArgumentException when input parameters are null
     */
    public Message newMessage(Customer customer, LoanApp loanApp,String subject){
        // Checking if the input parameters are not null
        if (customer == null || loanApp == null || subject == null) {
            throw new IllegalArgumentException("Input parameters cannot be null");
        }
        Double limit = loanApp.getLimit();
        Message newMessage = new Message();
        String messageBody = "SAYIN " + customer.getFirstName().toUpperCase() + " " + customer.getLastName().toUpperCase() +
                ", KREDİ BAŞVURUNUZ ";

        if (loanApp.getResult() == 0){
            messageBody = messageBody + "ONAYLANMADI.";
        }else {
            messageBody = messageBody + "ONAYLANDI. \nKREDİ LİMİTİNİZ: " + limit + " TL.";
        }
        messageBody += "\n\nSpringBoot Projesinden Selamlar.";

        newMessage.setTo(customer.getEmail());
        newMessage.setSubject(subject);
        newMessage.setBody(messageBody);

        return newMessage;
    }
}

