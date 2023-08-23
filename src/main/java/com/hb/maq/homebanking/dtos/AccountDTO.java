package com.hb.maq.homebanking.dtos;

import com.hb.maq.homebanking.models.Account;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    /** ATRIBUTOS */
    private Long id;
    private String number;
    private LocalDate creationDate;
    private double balance;
    private Set<TransactionDTO> transactions;

    /** CONSTRUCTORES */
    public AccountDTO() {}
    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();

        this.transactions = account.getTransactions()
                .stream().map(transaction -> new TransactionDTO(transaction))
                .collect(Collectors.toSet());
    }

    /** GETTERS */
    public Long getId() {
        return id;
    }
    public String getNumber() {
        return number;
    }
    public LocalDate getCreationDate() {
        return creationDate;
    }
    public double getBalance() {
        return balance;
    }
    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
}
