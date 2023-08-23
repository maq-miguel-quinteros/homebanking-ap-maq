package com.hb.maq.homebanking.dtos;

import com.hb.maq.homebanking.models.Card;
import com.hb.maq.homebanking.models.CardColor;
import com.hb.maq.homebanking.models.CardType;
import com.hb.maq.homebanking.models.Client;
import java.time.LocalDate;

public class CardDTO {

    /** ATRIBUTOS */
    private Long id;
    private String cardholder;
    private CardType type;
    private CardColor color;
    private String number;
    private int cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;

    /** CONSTRUCTORES */
    public CardDTO() {}
    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardholder = card.getCardholder();
        this.type = card.getType();
        this.color = card.getColor();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
    }

    /** GETTERS */
    public Long getId() {
        return id;
    }
    public String getCardholder() {
        return cardholder;
    }
    public CardType getType() {
        return type;
    }
    public CardColor getColor() {
        return color;
    }
    public String getNumber() {
        return number;
    }
    public int getCvv() {
        return cvv;
    }
    public LocalDate getFromDate() {
        return fromDate;
    }
    public LocalDate getThruDate() {
        return thruDate;
    }
}
