package com.hb.maq.homebanking.services.implement;

import com.hb.maq.homebanking.models.Account;
import com.hb.maq.homebanking.models.Transaction;
import com.hb.maq.homebanking.repositories.TransactionRepository;
import com.hb.maq.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    @Override
    public List<Transaction> findAllByAccount(Account account) {
        return transactionRepository.findByAccount(account);
    }
    /** READ ACCOUNT: FIND BY */

    /** DELETE TRANSACTION */
    @Override
    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }
}
