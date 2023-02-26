package com.example.loanapp.servicetests;


import com.example.loanapp.exception.CustomerNotFoundException;
import com.example.loanapp.exception.CustomerSaveException;
import com.example.loanapp.model.Customer;
import com.example.loanapp.repository.CustomerRepository;
import com.example.loanapp.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCustomer() {
        // Mock the repository's findAll method to return a list of customers
        List<Customer> expectedCustomers = new ArrayList<>();
        expectedCustomers.add(new Customer());
        when(repository.findAll()).thenReturn(expectedCustomers);

        // Call the service's getAllCustomer method
        List<Customer> actualCustomers = service.getAllCustomer();

        // Verify that the repository's findAll method was called once
        verify(repository, times(1)).findAll();

        // Verify that the returned list of customers is the same as the expected list of customers
        Assertions.assertEquals(expectedCustomers, actualCustomers);
    }

    @Test
    public void testGetCustomerById() throws CustomerNotFoundException {
        // Mock the repository's findById method to return an optional customer with the given id
        Customer expectedCustomer = new Customer();
        when(repository.findById(anyString())).thenReturn(Optional.of(expectedCustomer));

        // Call the service's getCustomerById method with a valid id
        Customer actualCustomer = service.getCustomerById("123");

        // Verify that the repository's findById method was called once with the given id
        verify(repository, times(1)).findById("123");

        // Verify that the returned customer is the same as the expected customer
        Assertions.assertEquals(expectedCustomer, actualCustomer);
    }

    @Test
    public void testGetCustomerByIdNotFound() {
        // Mock the repository's findById method to return an empty optional
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        // Call the service's getCustomerById method with an invalid id
        Assertions.assertThrows(CustomerNotFoundException.class, () -> {
            service.getCustomerById("456");
        });

        // Verify that the repository's findById method was called once with the given id
        verify(repository, times(1)).findById("456");
    }

    @Test
    public void testDeleteCustomerById() {
        // Call the service's deleteCustomerById method with a valid id
        service.deleteCustomerById("123");

        // Verify that the repository's deleteById method was called once with the given id
        verify(repository, times(1)).deleteById("123");
    }

    @Test
    public void testSaveCustomer() throws CustomerSaveException {
        // Mock the repository's save method to return the saved customer
        Customer expectedCustomer = new Customer();
        when(repository.save(any(Customer.class))).thenReturn(expectedCustomer);

        // Call the service's saveCustomer method with a new customer
        Customer newCustomer = new Customer();
        Customer actualCustomer = service.saveCustomer(newCustomer);

        // Verify that the repository's save method was called once with the new customer
        verify(repository, times(1)).save(newCustomer);

        // Verify that the returned customer is the same as the expected customer
        Assertions.assertEquals(expectedCustomer, actualCustomer);
    }
    @Test
    public void testUpdateCustomer() throws CustomerNotFoundException {
        // Create a mock customer
        Customer existingCustomer = new Customer();
        existingCustomer.setId("1");
        existingCustomer.setTckn("11111111111");
        existingCustomer.setFirstName("John");
        existingCustomer.setLastName("Doe");
        existingCustomer.setPhoneNum("555-1234");
        existingCustomer.setDeposit(1000.0);
        existingCustomer.setDateOfBirth("1990-01-01");
        existingCustomer.setIncomeMonthly(5000.0);

        // Create a mock updated customer
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId("1");
        updatedCustomer.setTckn("22222222222");
        updatedCustomer.setFirstName("Jane");
        updatedCustomer.setLastName("Doe");
        updatedCustomer.setPhoneNum("555-5678");
        updatedCustomer.setDeposit(2000.0);
        updatedCustomer.setDateOfBirth("1995-01-01");
        updatedCustomer.setIncomeMonthly(6000.0);

        // Set up the mock repository to return the existing customer when the findById method is called
        when(repository.findById("1")).thenReturn(Optional.of(existingCustomer));

        // Set up the mock repository to return the updated customer when the save method is called
        when(repository.save(updatedCustomer)).thenReturn(updatedCustomer);

        // Call the updateCustomer method with the mock customer id and the mock updated customer
        Customer result = service.updateCustomer("1", updatedCustomer);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result has the same id as the mock customer
        assertEquals("1", result.getId());

        // Assert that the result has the same properties as the mock updated customer
        assertEquals("22222222222", result.getTckn());
        assertEquals("Jane", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("555-5678", result.getPhoneNum());
        assertEquals(Optional.of(2000.0), Optional.of(result.getDeposit()));
        assertEquals("1995-01-01", result.getDateOfBirth());
        assertEquals(Optional.of(6000.0), Optional.of(result.getIncomeMonthly()));
    }
}