package com.hb.maq.homebanking.repositories;

import com.hb.maq.homebanking.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardRepository extends JpaRepository <Card, Long> {
}
