package com.example.loanapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("loan_app")
public class LoanApp {
    @Id
    private String id;
    private String tckn;
    private Integer result;
    private Double limit;
    private Long applicationTime;
    private Boolean dbStatus = false;

    public LoanApp(Integer result, Double limit,String tckn,Long applicationTime) {
        this.result = result;
        this.limit = limit;
        this.tckn = tckn;
        this.applicationTime = applicationTime;
    }


}
