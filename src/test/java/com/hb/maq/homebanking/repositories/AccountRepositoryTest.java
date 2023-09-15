package com.hb.maq.homebanking.repositories;

import com.hb.maq.homebanking.models.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    void existAccounts(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts, is(not(empty())));
    }
    @Test
    void accountNumberIsAString() {
        List<Account> accounts = accountRepository.findAll();
        for (Account account: accounts) {
            assertThat(account.getNumber(), isA(String.class));
        }
    }
}