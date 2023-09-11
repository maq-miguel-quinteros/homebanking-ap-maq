package com.hb.maq.homebanking.repositories;

import com.hb.maq.homebanking.models.Account;
import com.hb.maq.homebanking.models.Card;
import com.hb.maq.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CardRepository extends JpaRepository <Card, Long> {

    List<Card> findByClient(Client client);

    Card findByNumber(String number);
}
