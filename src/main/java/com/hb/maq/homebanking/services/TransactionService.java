package com.hb.maq.homebanking.services;

import com.hb.maq.homebanking.models.Account;
import com.hb.maq.homebanking.models.Transaction;

import java.util.List;


public interface TransactionService {

    /** CREATE TRANSACTION & UPDATE ACCOUNT: SAVE */
    void save(Transaction transaction);

    /** READ TRANSACTIONS: FIND ALL */
    List<Transaction> findAllByAccount(Account account);

    /** READ TRANSACTION: FIND BY */

    /** DELETE TRANSACTION */
    void deleteById(Long id);

}
