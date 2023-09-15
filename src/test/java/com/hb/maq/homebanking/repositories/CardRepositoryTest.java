package com.hb.maq.homebanking.repositories;

import com.hb.maq.homebanking.models.Card;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class CardRepositoryTest {

    @Autowired
    CardRepository cardRepository;

    @Test
    void existCards() {
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, is(not(empty())));
    }

    @Test
    void cardTypeCorrect() {
        List<Card> cards = cardRepository.findAll();
        for (Card card : cards ) {
            assertThat(card.getType().toString(), anyOf(containsString("CREDIT"),
                    containsString("DEBIT")));
        }
    }
}