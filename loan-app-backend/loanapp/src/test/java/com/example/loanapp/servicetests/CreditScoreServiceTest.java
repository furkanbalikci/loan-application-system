package com.example.loanapp.servicetests;


import com.example.loanapp.exception.EntityNotFoundException;
import com.example.loanapp.exception.InvalidInputException;
import com.example.loanapp.model.CreditScore;
import com.example.loanapp.repository.CreditScoreRepository;
import com.example.loanapp.service.CreditScoreService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreditScoreServiceTest {

    @InjectMocks
    private CreditScoreService creditScoreService;

    @Mock
    private CreditScoreRepository creditScoreRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCreditScoreById() throws EntityNotFoundException {
        CreditScore creditScore = new CreditScore("1", "12345678901", 750);
        when(creditScoreRepository.findById("1")).thenReturn(Optional.of(creditScore));
        CreditScore returnedCreditScore = creditScoreService.getCreditScoreById("1");
        assertNotNull(returnedCreditScore);
        assertEquals(returnedCreditScore.getTckn(), creditScore.getTckn());
        assertEquals(returnedCreditScore.getCreditScore(), creditScore.getCreditScore());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetCreditScoreById_EntityNotFoundException() throws EntityNotFoundException {
        when(creditScoreRepository.findById(anyString())).thenReturn(Optional.empty());
        creditScoreService.getCreditScoreById("1");
    }

    @Test
    public void testGetCreditScoreByTckn() throws EntityNotFoundException, InvalidInputException {
        CreditScore creditScore = new CreditScore("1", "12345678901", 750);
        when(creditScoreRepository.getCreditScoreByTckn("12345678901")).thenReturn(creditScore);
        CreditScore returnedCreditScore = creditScoreService.getCreditScoreByTckn("12345678901");
        assertNotNull(returnedCreditScore);
        assertEquals(returnedCreditScore.getId(), creditScore.getId());
        assertEquals(returnedCreditScore.getCreditScore(), creditScore.getCreditScore());
    }

    @Test(expected = InvalidInputException.class)
    public void testGetCreditScoreByTckn_InvalidInputException() throws EntityNotFoundException, InvalidInputException {
        creditScoreService.getCreditScoreByTckn(null);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetCreditScoreByTckn_EntityNotFoundException() throws EntityNotFoundException, InvalidInputException {
        when(creditScoreRepository.getCreditScoreByTckn(anyString())).thenReturn(null);
        creditScoreService.getCreditScoreByTckn("12345678901");
    }
}
