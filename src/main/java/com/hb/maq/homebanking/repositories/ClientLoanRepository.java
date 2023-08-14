package com.hb.maq.homebanking.repositories;

import com.hb.maq.homebanking.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientLoanRepository extends JpaRepository <ClientLoan, Long> {
}
