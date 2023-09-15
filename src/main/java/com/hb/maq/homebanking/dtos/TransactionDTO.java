package com.hb.maq.homebanking.dtos;

import com.hb.maq.homebanking.models.Transaction;
import com.hb.maq.homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {

    /** ATRIBUTOS */
    private Long id;
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime date;
    private double currentBalance;

    /** CONSTRUCTORES */
    public TransactionDTO() {}
    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.currentBalance = transaction.getCurrentBalance();
    }

    /** GETTERS */
    public Long getId() {
        return id;
    }
    public TransactionType getType() {
        return type;
    }
    public double getAmount() {
        return amount;
    }
    public String getDescription() {
        return description;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public double getCurrentBalance() {
        return currentBalance;
    }
}
