package com.example.loanapp.controller;

import com.example.loanapp.exception.CustomerNotFoundException;
import com.example.loanapp.exception.CustomerSaveException;
import com.example.loanapp.model.Customer;
import com.example.loanapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/getCustomerById/{id}")
    public Customer getCustomerById(@PathVariable String id) throws CustomerNotFoundException {
        return customerService.getCustomerById(id);
    }

    @GetMapping("/getCustomer/{tckn}")
    public Customer getOneCustomerByTckn(@PathVariable String tckn){
        return customerService.getCustomerByTckn(tckn);
    }

    @GetMapping("/allCustomer")
    //Get all customers.
    public List<Customer> getAllCustomer(){
        return customerService.getAllCustomer();
    }

    @PostMapping("/createCustomer")
    public Customer createCustomer(@RequestBody Customer newCustomer) throws CustomerSaveException {
        return customerService.saveCustomer(newCustomer);
    }

    @PutMapping("/updateCustomer/{customerId}")
    public Customer updateCustomer(@PathVariable String customerId, @RequestBody Customer newCustomer) throws CustomerNotFoundException {
        return customerService.updateCustomer(customerId,newCustomer);
    }

    @DeleteMapping("/deleteCustomer/{customerId}")
    public void deleteCustomer(@PathVariable String customerId){
        customerService.deleteCustomerById(customerId);
    }
}
