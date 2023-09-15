package com.hb.maq.homebanking;

import com.hb.maq.homebanking.models.Loan;
import com.hb.maq.homebanking.repositories.LoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoriesTest {

    @Autowired
    private LoanRepository loanRepository;

    @Test
    public void existLoans(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans,is(not(empty())));
    }

    @Test
    public void existPersonalLoan(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
    }

    /*
    @Test
    void correctListOfPayments(){
        List<Loan> loans = loanRepository.findAll();
        for (Loan loan : loans ) {
            if (loan.getName().equals("Hipotecario")){
                assertThat(loan.getPayments(), containsInAnyOrder(12,24,36,48,60));
            }
            if (loan.getName().equals("Personal")){
                assertThat(loan.getPayments().toArray(), arrayContaining(6,12,24));
            }
            if (loan.getName().equals("Automotriz")){
                assertThat(loan.getPayments().toArray(), arrayContaining(6,12,24,36));
            }
        }
    }
*/
}
