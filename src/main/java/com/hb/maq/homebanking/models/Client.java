package com.hb.maq.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;


    /** Es una colección del tipo account donde voy a tener detalles de cada cuenta del client */
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    Set<Account> accounts = new HashSet<>();

    /** getter del tipo de dato Account */
    public Set<Account> getAccounts(){
        return accounts;
    }

    /** método configurar el cliente en una account y sumar un account a la colección accounts */
    public void addAccounts(Account account){
        account.setClient(this);
        accounts.add(account);
    }

    /** colección de tipo ClientLoan a la que solo le llamamos loan */
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> loans = new HashSet<>();

    /** getter de tipo de dato ClientLoan al que solo llamamos loans */
    public Set<ClientLoan> getLoans(){ return loans; }


    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    public Set<Card> getCards(){ return cards;}

    public void addCards(Card card){
        card.setClient(this);
        cards.add(card);
    }


    private String firstName;
    private String lastName;
    private String email;

    public Client() {
    }

    public Client(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }
}
