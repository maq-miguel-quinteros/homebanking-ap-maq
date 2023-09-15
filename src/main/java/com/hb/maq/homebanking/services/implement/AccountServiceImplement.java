package com.hb.maq.homebanking.services.implement;

import com.hb.maq.homebanking.dtos.AccountDTO;
import com.hb.maq.homebanking.models.Account;
import com.hb.maq.homebanking.models.Client;
import com.hb.maq.homebanking.repositories.AccountRepository;
import com.hb.maq.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplement implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /** CREATE ACCOUNT & UPDATE ACCOUNT: SAVE */
    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    /** READ ACCOUNT: FIND BY */

    @Override
    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }
    @Override
    public AccountDTO findByIdToAccountDTO(Long id) {
        return accountRepository.findById(id)
                .map(account -> new AccountDTO(account))
                .orElse(null);
    }
    @Override
    public List<AccountDTO> findByClientToListAccountDTO(Client client) {
        return accountRepository.findByClient(client).stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }

    /** DELETE ACCOUNT */
    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }
}
