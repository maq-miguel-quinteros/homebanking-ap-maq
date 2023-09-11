package com.hb.maq.homebanking.services.implement;

import com.hb.maq.homebanking.models.ClientLoan;
import com.hb.maq.homebanking.repositories.ClientLoanRepository;
import com.hb.maq.homebanking.services.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientLoanServiceImplement implements ClientLoanService {

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    /** CREATE ACCOUNT & UPDATE ACCOUNT: SAVE */
    @Override
    public void save(ClientLoan clientLoan) {
        clientLoanRepository.save(clientLoan);
    }
    /** READ ACCOUNTS: FIND ALL */

    /** READ ACCOUNT: FIND BY */
}
