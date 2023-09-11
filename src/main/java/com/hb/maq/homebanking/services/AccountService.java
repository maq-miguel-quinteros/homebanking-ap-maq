package com.hb.maq.homebanking.services;

import com.hb.maq.homebanking.dtos.AccountDTO;
import com.hb.maq.homebanking.models.Account;
import com.hb.maq.homebanking.models.Client;

import java.util.List;

public interface AccountService {

    /** CREATE ACCOUNT & UPDATE ACCOUNT: SAVE */
    void save(Account account);

    /** READ ACCOUNTS: FIND ALL */

    /** READ ACCOUNT: FIND BY */
    Account findByNumber(String number);
    AccountDTO findByIdToAccountDTO(Long id);
    List<AccountDTO> findByClientToListAccountDTO(Client client);
}
