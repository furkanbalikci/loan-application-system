package com.example.loanapp.repository;

import com.example.loanapp.model.LoanApp;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoanAppRepository extends MongoRepository<LoanApp,String> {
    LoanApp getLoanAppByTckn(String tckn);
}
