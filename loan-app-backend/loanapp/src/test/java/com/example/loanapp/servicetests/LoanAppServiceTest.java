package com.example.loanapp.servicetests;

import com.example.loanapp.exception.EntityNotFoundException;
import com.example.loanapp.exception.InvalidInputException;
import com.example.loanapp.model.CreditScore;
import com.example.loanapp.model.Customer;
import com.example.loanapp.model.LoanApp;
import com.example.loanapp.repository.LoanAppRepository;
import com.example.loanapp.service.CreditScoreService;
import com.example.loanapp.service.LoanAppService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LoanAppServiceTest {
    @Mock
    private LoanAppRepository loanAppRepository;
    @Mock
    private CreditScoreService creditScoreService;

    @InjectMocks
    private LoanAppService loanAppService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveLoanAppWithValidCreditScore() throws EntityNotFoundException, InvalidInputException {
        // Arrange
        Customer customer = new Customer();
        customer.setTckn("12345678901");
        customer.setIncomeMonthly(10000.0);
        customer.setDeposit(5000.0);

        CreditScore creditScore = new CreditScore();
        creditScore.setTckn("12345678901");
        creditScore.setCreditScore(800);

        when(creditScoreService.getCreditScoreByTckn("12345678901")).thenReturn(creditScore);

        LoanApp savedLoanApp = new LoanApp();
        savedLoanApp.setTckn("12345678901");
        savedLoanApp.setResult(1);
        savedLoanApp.setLimit(12500.0);
        savedLoanApp.setApplicationTime(new Timestamp(System.currentTimeMillis()).getTime());

        when(loanAppRepository.save(any(LoanApp.class))).thenReturn(savedLoanApp);

        // Act
        LoanApp loanApp = loanAppService.saveLoanApp(customer);

        // Assert
        verify(creditScoreService, times(1)).getCreditScoreByTckn("12345678901");
        verify(loanAppRepository, times(1)).save(any(LoanApp.class));

        assertEquals("12345678901", loanApp.getTckn());
        assertEquals(1, loanApp.getResult());
        assertEquals(12500.0, loanApp.getLimit());
    }

    @Test
    public void testSaveLoanAppWithInvalidCreditScore() throws EntityNotFoundException, InvalidInputException {
        // Arrange
        Customer customer = new Customer();
        customer.setTckn("12345678901");
        customer.setIncomeMonthly(4000.0);
        customer.setDeposit(0.0);

        CreditScore creditScore = new CreditScore();
        creditScore.setTckn("12345678901");
        creditScore.setCreditScore(300);

        when(creditScoreService.getCreditScoreByTckn("12345678901")).thenReturn(creditScore);
        LoanApp savedLoanApp = new LoanApp();
        savedLoanApp.setTckn("12345678901");
        savedLoanApp.setResult(0);
        savedLoanApp.setLimit(0.0);
        savedLoanApp.setApplicationTime(new Timestamp(System.currentTimeMillis()).getTime());

        when(loanAppRepository.save(any(LoanApp.class))).thenReturn(savedLoanApp);
        // Act
        LoanApp loanApp = loanAppService.saveLoanApp(customer);

        // Assert
        verify(creditScoreService, times(1)).getCreditScoreByTckn("12345678901");
        verify(loanAppRepository, times(1)).save(any(LoanApp.class));
        assertEquals("12345678901", loanApp.getTckn());
        assertEquals(0, loanApp.getResult());
        assertEquals(0.0, loanApp.getLimit());


    }

    @Test
    public void testGetLoanApp() {
        // Create a sample customer with TCKN and income
        String tckn = "12345678900";

        // Create a LoanApp object with the expected details
        LoanApp expectedLoanApp = new LoanApp();
        expectedLoanApp.setTckn(tckn);
        expectedLoanApp.setLimit(10000.0);
        expectedLoanApp.setResult(1);
        expectedLoanApp.setApplicationTime(System.currentTimeMillis());

        // Mock the LoanAppRepository to return the expectedLoanApp when getLoanAppByTckn is called with tckn
        when(loanAppRepository.getLoanAppByTckn(tckn)).thenReturn(expectedLoanApp);

        // Call the getLoanApp function and get the actual LoanApp object
        LoanApp actualLoanApp = loanAppService.getLoanApp(tckn);

        // Verify that the returned LoanApp object matches the expectedLoanApp object
        assertEquals(expectedLoanApp, actualLoanApp);
    }

}