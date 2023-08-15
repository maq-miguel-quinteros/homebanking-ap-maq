package com.hb.maq.homebanking.repositories;

import com.hb.maq.homebanking.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository <Card, Long> {
}
