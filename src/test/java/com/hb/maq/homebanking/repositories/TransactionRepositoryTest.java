package com.hb.maq.homebanking.repositories;

import com.hb.maq.homebanking.models.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class TransactionRepositoryTest {

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    void existTransactions(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, is(not(empty())));
    }

    @Test
    void valueCorrespondsToType(){
        List<Transaction> transactions = transactionRepository.findAll();
        for (Transaction transaction : transactions ) {
            if (transaction.getType().equals("CREDIT")){
                assertThat(transaction.getAmount(), greaterThanOrEqualTo(new Double(0)));
            }
            if (transaction.getType().equals("DEBIT")){
                assertThat(transaction.getAmount(), lessThanOrEqualTo(new Double(0)));
            }
        }
    }
}