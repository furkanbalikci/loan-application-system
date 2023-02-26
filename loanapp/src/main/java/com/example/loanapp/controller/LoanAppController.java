package com.example.loanapp.controller;

import com.example.loanapp.dto.LoanStatusCheckDto;
import com.example.loanapp.exception.CustomerSaveException;
import com.example.loanapp.exception.EntityNotFoundException;
import com.example.loanapp.exception.InvalidInputException;
import com.example.loanapp.exception.MailSendingException;
import com.example.loanapp.model.LoanApp;
import com.example.loanapp.service.CustomerService;
import com.example.loanapp.service.EmailService;
import com.example.loanapp.service.LoanAppService;

import com.example.loanapp.model.CreditScore;
import com.example.loanapp.model.Customer;
import com.example.loanapp.service.CreditScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;


@RestController
@RequestMapping("/api/v1")
public class LoanAppController {

    @Autowired
    private CreditScoreService creditScoreService;
    @Autowired
    private LoanAppService loanAppService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CustomerService customerService;


    @GetMapping("/getCreditScore/{creditScoreId}")
    public CreditScore getCreditScore(@PathVariable String creditScoreId) throws EntityNotFoundException {
        return creditScoreService.getCreditScoreById(creditScoreId);
    }

    @GetMapping("/getCreditScoreByTckn/{tckn}")
    public CreditScore getCreditScoreByTckn(@PathVariable String tckn) throws InvalidInputException, EntityNotFoundException {
        return creditScoreService.getCreditScoreByTckn(tckn);
    }
    @GetMapping("/creditResult/{tckn}")
    public LoanApp showCreditResult(@PathVariable String tckn){
        return loanAppService.getLoanApp(tckn);
    }

    @PostMapping("/loanapp")
    public LoanApp addLoanApplication(@RequestBody Customer customer) throws MailSendingException {

        Customer foundCustomer = customerService.getCustomerWithNullId(customer.getTckn());

        LoanApp loanApp = loanAppService.getLoanApp(customer.getTckn());
        LoanApp newLoanApp;

        if (loanApp == null) {
            newLoanApp = loanAppService.saveLoanApp(customer);
            emailService.sendMail(emailService.newMessage(customer,newLoanApp,"KREDİ BAŞVURU SONUCU"));
            newLoanApp.setDbStatus(true);
            loanApp = newLoanApp;
            if (!customer.equals(foundCustomer)){
                try {
                    customerService.saveCustomer(customer);
                } catch (CustomerSaveException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return loanApp;
    }

    @PostMapping("/loanStatusCheck")
    public LoanStatusCheckDto loanStatusCheck(@RequestParam("tckn") String tckn,
                                              @RequestParam("dateOfBirth") String dateOfBirth){

        if (tckn == null || dateOfBirth == null){
            return null;
        }else {
            Customer customer = customerService.getCustomerByTckn(tckn);
            if (customer.getDateOfBirth().equals(dateOfBirth)){
                LoanApp loanApp = loanAppService.getLoanApp(tckn);
                return new LoanStatusCheckDto(customer.getTckn(),customer.getFirstName(),
                        customer.getLastName(),customer.getIncomeMonthly(),customer.getEmail(),
                        customer.getPhoneNum(),customer.getDateOfBirth(),customer.getDeposit(),
                        loanApp.getResult(),loanApp.getLimit(),loanApp.getApplicationTime());
            }else{
                return null;
            }

        }
    }
}
