package com.example.loanapp.service;

import com.example.loanapp.exception.EntityNotFoundException;
import com.example.loanapp.exception.InvalidInputException;
import com.example.loanapp.model.CreditScore;
import com.example.loanapp.repository.CreditScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditScoreService {
    @Autowired
    private CreditScoreRepository creditScoreRepository;

    // method to get the credit score of a customer by id
    public CreditScore getCreditScoreById(String creditScoreId) throws EntityNotFoundException {
        // Check if the credit score with the given id exists
        CreditScore creditScore = creditScoreRepository.findById(creditScoreId).orElse(null);
        if (creditScore == null) {
            throw new EntityNotFoundException("Credit Score with id " + creditScoreId + " not found");
        }
        return creditScore;
    }

    // method to get the credit score of a customer by TCKN
    public CreditScore getCreditScoreByTckn(String tckn) throws EntityNotFoundException, InvalidInputException {
        // Check if the input TCKN is valid
        if (tckn == null || tckn.trim().length() == 0) {
            throw new InvalidInputException("Invalid TCKN: " + tckn);
        }
        // Check if the credit score with the given TCKN exists
        CreditScore creditScore = creditScoreRepository.getCreditScoreByTckn(tckn);
        if (creditScore == null) {
            throw new EntityNotFoundException("Credit Score with TCKN " + tckn + " not found");
        }
        return creditScore;
    }
}