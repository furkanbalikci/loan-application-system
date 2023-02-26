package com.example.loanapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Customer {
    @Id
    private String id;
    private String tckn;
    @Field("first_name")
    private String firstName;
    @Field("last_name")
    private String lastName;
    @Field("income_monthly")
    private Double incomeMonthly;
    @Field("email")
    private String email;
    @Field("phone_num")
    private String phoneNum;
    @Field("date_of_birth")
    private String dateOfBirth;
    private Double deposit;



    public Customer(String tckn, String firstName, String lastName, Double incomeMonthly, String email, String phoneNum, String dateOfBirth,Double deposit) {
        this.tckn = tckn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.incomeMonthly = incomeMonthly;
        this.email = email;
        this.phoneNum = phoneNum;
        this.dateOfBirth = dateOfBirth;
        this.deposit = deposit;
    }


    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

}
