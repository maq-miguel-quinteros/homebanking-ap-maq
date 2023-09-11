package com.hb.maq.homebanking.services.implement;

import com.hb.maq.homebanking.dtos.LoanDTO;
import com.hb.maq.homebanking.models.Loan;
import com.hb.maq.homebanking.repositories.LoanRepository;
import com.hb.maq.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImplement implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    /** CREATE ACCOUNT & UPDATE ACCOUNT: SAVE */
    @Override
    public void save(Loan loan) {
        loanRepository.save(loan);
    }

    /** READ ACCOUNTS: FIND ALL */
    @Override
    public List<LoanDTO> findAllToListLoanDTO() {
        return loanRepository.findAll().stream()
                .map(loan -> new LoanDTO(loan))
                .collect(Collectors.toList());
    }
    /** READ ACCOUNT: FIND BY */
    @Override
    public Loan findById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }
}
