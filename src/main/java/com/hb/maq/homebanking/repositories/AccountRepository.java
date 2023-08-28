package com.hb.maq.homebanking.repositories;

import com.hb.maq.homebanking.models.Account;
import com.hb.maq.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository <Account, Long> {
    public List<Account> findByClient(Client client);
}
