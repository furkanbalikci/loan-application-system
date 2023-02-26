package com.example.loanapp.servicetests;

import com.example.loanapp.exception.MailSendingException;
import com.example.loanapp.model.Customer;
import com.example.loanapp.model.LoanApp;
import com.example.loanapp.model.Message;
import com.example.loanapp.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Arrays;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class EmailServiceTest {

    @Mock
    private JavaMailSender mailSenderMock;

    @InjectMocks
    private EmailService emailService;

    @Mock
    private Logger loggerMock;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendMail() throws MailSendingException {
        // Arrange
        Message message = new Message("test@example.com", "Test subject", "Test body");
        SimpleMailMessage expectedMailMessage = new SimpleMailMessage();
        expectedMailMessage.setTo(message.getTo());
        expectedMailMessage.setSubject(message.getSubject());
        expectedMailMessage.setText(message.getBody());

        // Act
        emailService.sendMail(message);

        // Assert
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSenderMock).send(captor.capture());
        SimpleMailMessage actualMailMessage = captor.getValue();
        Assertions.assertArrayEquals(expectedMailMessage.getTo(), actualMailMessage.getTo());
        Assertions.assertEquals(expectedMailMessage.getSubject(), actualMailMessage.getSubject());
        Assertions.assertEquals(expectedMailMessage.getText(), actualMailMessage.getText());
    }

    @Test
    public void testSendMailThrowsException() throws Exception {
        // Arrange
        Message message = new Message("test@example.com", "Test subject", "Test body");
        doThrow(new RuntimeException("Test exception")).when(mailSenderMock).send(any(SimpleMailMessage.class));

        // Act and Assert
        assertThrows(MailSendingException.class, () -> emailService.sendMail(message));
        verify(loggerMock).error(anyString(), any(Throwable.class));
    }



    @Test
    public void testNewMessage() {
        // Arrange
        Customer customer = new Customer("John", "Doe", "johndoe@example.com");
        LoanApp loanApp = new LoanApp();
        loanApp.setLimit(5000.0);
        loanApp.setResult(1);
        String subject = "Loan application result";
        String expectedBody = "SAYIN JOHN DOE, KREDİ BAŞVURUNUZ ONAYLANDI. KREDİ LİMİTİNİZ: 5000.0 TL.\n\nSpringBoot Projesinden Selamlar.";

        // Act
        Message message = emailService.newMessage(customer, loanApp, subject);

        // Assert
        Assertions.assertEquals(customer.getEmail(), message.getTo());
        Assertions.assertEquals(subject, message.getSubject());
        Assertions.assertEquals(expectedBody, message.getBody());
    }

    @Test
    public void testNewMessageNullInput() {
        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            emailService.newMessage(null, null, null);
        });
    }
}
