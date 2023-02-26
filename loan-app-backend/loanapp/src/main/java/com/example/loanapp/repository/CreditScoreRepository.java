package com.example.loanapp.repository;

import com.example.loanapp.model.CreditScore;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditScoreRepository extends MongoRepository<CreditScore,String> {

     CreditScore getCreditScoreByTckn(String tckn);
}
