package com.example.loanapp.repository;

import com.example.loanapp.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer,String> {
    Customer getCustomerByTckn(String tckn);
}
