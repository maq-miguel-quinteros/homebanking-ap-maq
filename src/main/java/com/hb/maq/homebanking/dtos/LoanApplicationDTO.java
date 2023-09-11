package com.hb.maq.homebanking.dtos;

import javax.persistence.ElementCollection;
import java.util.List;

public class LoanApplicationDTO {

    /** ATRIBUTOS */
    private Long loanId;
    private Double amount;
    private Integer payments;
    private String toAccountNumber;

    /** GETTERS */
    public Long getLoanId() {
        return loanId;
    }
    public Double getAmount() {
        return amount;
    }
    public Integer getPayments() {
        return payments;
    }
    public String getToAccountNumber() {
        return toAccountNumber;
    }
}
