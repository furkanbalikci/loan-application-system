package com.example.loanapp.service;

import com.example.loanapp.exception.EntityNotFoundException;
import com.example.loanapp.model.CreditScore;
import com.example.loanapp.model.Customer;
import com.example.loanapp.model.LoanApp;
import com.example.loanapp.repository.LoanAppRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class LoanAppService {
    @Autowired
    private LoanAppRepository loanAppRepository;
    @Autowired
    private CreditScoreService creditScoreService;

    // Constants used in the calculation of loan limits
    private final int CREDIT_LIMIT_MULTIPLIER = 4;
    private final int CREDIT_LIMIT_LOWER = 500;
    private final int CREDIT_LIMIT_UPPER = 1000;

    Logger logger = LoggerFactory.getLogger(LoanAppService.class);
    /**
     * This method saves the loan application of a customer
     *
     * @param customer The customer object with the details of the loan application
     * @return The saved loan application object
     */
    public LoanApp saveLoanApp(Customer customer){

        // Set the TCKN of the customer to the loan application object
        LoanApp newLoanApp = new LoanApp();
        String tckn = customer.getTckn();

        newLoanApp.setTckn(tckn);
/*        newLoanApp.setResult(0);
        newLoanApp.setLimit(0.0);*/
        Double income = customer.getIncomeMonthly();

        double limit;
        Double deposit = 0.0;
        // If the customer has a deposit, use it in the calculation of the loan limit
        if (customer.getDeposit() != null){
            deposit = customer.getDeposit();
        }

        try {
            // Get the credit score of the customer from the credit score service
            CreditScore creditScore = creditScoreService.getCreditScoreByTckn(tckn);
            Integer cScore = creditScore.getCreditScore();

            if (cScore < CREDIT_LIMIT_LOWER){
                newLoanApp.setResult(0);
                newLoanApp.setLimit(0.0);
            } else if (cScore < CREDIT_LIMIT_UPPER){
                if (income < 5000){
                    newLoanApp.setResult(1);
                    limit = 10000.00 + deposit * 0.10;
                    newLoanApp.setLimit(limit);
                } else if (income < 10000) {
                    newLoanApp.setResult(1);
                    limit = 20000.00 + deposit * 0.20;
                    newLoanApp.setLimit(limit);
                } else{
                    newLoanApp.setResult(1);
                    limit = (income * CREDIT_LIMIT_MULTIPLIER / 2) + (deposit * 0.25);
                    newLoanApp.setLimit(limit);
                }
            } else {
                newLoanApp.setResult(1);
                limit = (income * CREDIT_LIMIT_MULTIPLIER ) + (deposit * 0.5);
                newLoanApp.setLimit(limit);
            }
        } catch (Exception e) {
            // Log the error if an exception occurs while fetching the credit score
            newLoanApp.setResult(0);
            newLoanApp.setLimit(0.0);
            logger.error("Error while fetching credit score for TCKN " + tckn, e);
        } catch (EntityNotFoundException e) {
            newLoanApp.setResult(0);
            newLoanApp.setLimit(0.0);
            logger.info("Entity not found and added new customer for TCKN " + tckn, e);

        }

        // Set the application time of the loan application to the current time
        newLoanApp.setApplicationTime(new Timestamp(System.currentTimeMillis()).getTime());

        try {
            return loanAppRepository.save(newLoanApp);
        } catch (Exception e) {
            logger.warn("Error while saving loan application for TCKN " + tckn, e);
            return null;
        }

    }

    /**
     * This method gets the loan application by customer TCKN.
     *
     * @param tckn The TCKN String variable with the details of the loan aplication.
     * @return The fetched loan application object.*/
    public LoanApp getLoanApp(String tckn){
        try {
            return loanAppRepository.getLoanAppByTckn(tckn);
        } catch (Exception e) {
            logger.error("Error while fetching loan application for TCKN " + tckn, e);
            return null;
        }

    }



}
