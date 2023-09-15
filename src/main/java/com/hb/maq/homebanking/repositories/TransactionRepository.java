package com.hb.maq.homebanking.repositories;

import com.hb.maq.homebanking.models.Account;
import com.hb.maq.homebanking.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TransactionRepository extends JpaRepository <Transaction, Long>{

    List<Transaction> findByAccount(Account account);
}
