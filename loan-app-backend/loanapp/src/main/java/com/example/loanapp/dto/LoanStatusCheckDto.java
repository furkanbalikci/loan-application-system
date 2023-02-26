package com.example.loanapp.dto;

import com.example.loanapp.model.Customer;
import com.example.loanapp.model.LoanApp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanStatusCheckDto {
    private String tckn;
    private String firstName;
    private String lastName;
    private Double incomeMonthly;
    private String email;
    private String phoneNum;
    private String dateOfBirth;
    private Double deposit;
    private Integer result;
    private Double limit;
    private Long applicationTime;


}
