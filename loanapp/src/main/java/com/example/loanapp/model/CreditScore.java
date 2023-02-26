package com.example.loanapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("credit_score")
public class CreditScore {
    @Id
    private String id;
    private String tckn;
    @Field("credit_score")
    private Integer creditScore;

    public CreditScore(String tckn,Integer creditScore) {
        this.tckn = tckn;
        this.creditScore = creditScore;

    }

}
