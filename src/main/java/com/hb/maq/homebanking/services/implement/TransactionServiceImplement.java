package com.hb.maq.homebanking.services.implement;

import com.hb.maq.homebanking.models.Transaction;
import com.hb.maq.homebanking.repositories.TransactionRepository;
import com.hb.maq.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImplement implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    /** CREATE ACCOUNT & UPDATE ACCOUNT: SAVE */
    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }
    /** READ ACCOUNTS: FIND ALL */

    /** READ ACCOUNT: FIND BY */
}
