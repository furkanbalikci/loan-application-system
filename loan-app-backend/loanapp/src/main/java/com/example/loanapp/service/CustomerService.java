package com.example.loanapp.service;

import com.example.loanapp.exception.CustomerNotFoundException;
import com.example.loanapp.exception.CustomerSaveException;
import com.example.loanapp.model.Customer;
import com.example.loanapp.model.LoanApp;
import com.example.loanapp.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CustomerService {

    // Autowiring the CustomerRepository
    @Autowired
    private CustomerRepository repository;
    @Autowired
    private LoanAppService loanAppService;

    // Initializing a logger for this class
    Logger logger = LoggerFactory.getLogger(CustomerService.class);

    // Method to retrieve all customers from the repository
    public List<Customer> getAllCustomer() {
        return repository.findAll();
    }

    // Method to retrieve a customer by id from the repository
    public Customer getCustomerById(String id) throws CustomerNotFoundException {
        // Searching for a customer with the given id
        Optional<Customer> customer = repository.findById(id);
        // If a customer with the given id exists
        if (customer.isPresent()) {
            // Return the customer
            return customer.get();
        } else {
            // If no customer is found, throw an exception
            throw new CustomerNotFoundException("Customer with id: " + id + " not found");
        }
    }

    // Method to delete a customer by id from the repository
    public void deleteCustomerById(String id) throws DataIntegrityViolationException {
        try {
            // Attempt to delete the customer
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            // Log the error in case of an exception
            logger.error("Error while deleting customer with id: " + id + ". " + e.getMessage());
            // Throw the exception
            throw e;
        }
    }

    // Method to save a new customer to the repository
    public Customer saveCustomer(Customer newCustomer) throws CustomerSaveException {
        try {
            // Attempt to save the new customer
            if (repository.getCustomerByTckn(newCustomer.getTckn()) == null){
                return repository.save(newCustomer);
            }
        } catch (Exception e) {
            // Log the error in case of an exception
            logger.error("Error while saving customer. " + e.getMessage());
            // Throw a new exception
            throw new CustomerSaveException("Error while saving customer", e.getMessage());
        }
        return null;
    }

    // Method to update an existing customer in the repository
    public Customer updateCustomer(String customerId, Customer newCustomer) {
        // Searching for the existing customer by id
        Optional<Customer> customer = repository.findById(customerId);
        Customer foundCustomer = null;
        // If the customer exists
        if (customer.isPresent()) {
            try {
                // Get the existing customer
                foundCustomer = customer.get();
                // Update the customer's properties with the new values
                foundCustomer.setTckn(newCustomer.getTckn());
                foundCustomer.setFirstName(newCustomer.getFirstName());
                foundCustomer.setLastName(newCustomer.getLastName());
                foundCustomer.setPhoneNum(newCustomer.getPhoneNum());
                foundCustomer.setDeposit(newCustomer.getDeposit());
                foundCustomer.setDateOfBirth(newCustomer.getDateOfBirth());
                foundCustomer.setIncomeMonthly(newCustomer.getIncomeMonthly());
                // Save the updated customer
                repository.save(foundCustomer);
            } catch (Exception e) {
                // Log the error in case of an exception
                logger.error("Error while updating customer. " + e.getMessage());
            }
        }
        return foundCustomer;
    }

    public Customer getCustomerByTckn(String tckn){
        return repository.getCustomerByTckn(tckn);
    }
    public Customer getCustomerWithNullId(String tckn){
        Customer customer = getCustomerByTckn(tckn);
        if (customer != null) {
            customer.setId(null);

        }
        return customer;
    }
}