package com.hb.maq.homebanking.services;

import com.hb.maq.homebanking.dtos.LoanDTO;
import com.hb.maq.homebanking.models.Account;
import com.hb.maq.homebanking.models.Loan;


import java.util.List;


public interface LoanService {

    /** CREATE ACCOUNT & UPDATE ACCOUNT: SAVE */
    void save (Loan loan);

    /** READ ACCOUNTS: FIND ALL */
    List<LoanDTO> findAllToListLoanDTO();

    /** READ ACCOUNT: FIND BY */
    Loan findById(Long id);
}
