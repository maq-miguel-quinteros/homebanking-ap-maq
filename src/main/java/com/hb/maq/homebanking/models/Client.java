package com.hb.maq.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/** Una entidad es la representación de información que nosotros necesitamos en nuestra aplicación.
 * Esta entidad podría ser un usuario, un producto o cualquier dato que nuestra aplicación necesita mantener
 * persistente para luego recuperarla cuando la necesite.
 * Una entidad para Spring Boot es un objeto, elemento o ‘cosa’ con atributos particulares que lo distinguen.
 * Spring principalmente usa entidades para reconocer cuáles clases representan el modelo de datos.
 * Con esto, Spring puede realizar las acciones habituales sobre la base de datos para la entidad,
 * como insertar, eliminar, buscar, etc.
 * https://gustavopeiretti.com/spring-boot-entities-conceptos-iniciales/#:~:text=Una%20entidad%20es%20la%20representaci%C3%B3n,luego%20recuperarla%20cuando%20la%20necesite. */
@Entity
public class Client {

    /** ATRIBUTOS */
    /** Al igual que en las tablas, las entidades también requieren un identificador(@Id),
     * dicho identificador deberá de diferenciar a la entidad del resto. Como regla general, todas las entidades
     * deberán definir in ID, de lo contrario provocaremos que el EntityManager marque error a la hora de instanciarlo.
     * El ID es importante porque será utilizando por EntityManager a la hora de persistir un objeto,
     * Es por este que puede determinar sobre que registro hacer el select, update o delete.
     * JPA soporta ID simples de un solo campo o ID complejos, formados por más de un campo
     * https://www.oscarblancarteblog.com/2016/11/01/definir-llave-primaria-id/ */
    @Id
    /** El GenerationType.AUTO es el tipo de generación por defecto y permite que
     * el proveedor de persistencia elija la estrategia de generación.
     * Si usa Hibernate como su proveedor de persistencia, selecciona una estrategia de generación
     * basada en el dialecto específico de la base de datos.
     * https://www.adictosaltrabajo.com/2019/12/26/hibernate-uso-de-generationtype-y-otras-anotaciones/ */
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    /** Cuando usa un @GenericGenerator que hace referencia a la estrategia nativa,
     * Hibernate usa la estrategia soportada de forma nativa por el Dialecto configurado
     * https://www.adictosaltrabajo.com/2019/12/26/hibernate-uso-de-generationtype-y-otras-anotaciones/ */
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> loans = new HashSet<>();
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    /** CONSTRUCTORES */
    public Client() {}
    public Client(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    /** MÉTODOS PROPIOS */
    public void addAccounts(Account account){
        account.setClient(this);
        accounts.add(account);
    }
    public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setClient(this);
        loans.add(clientLoan);
    }
    public void addCards(Card card){
        card.setClient(this);
        cards.add(card);
    }

    /** GETTERS Y SETTERS */
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Set<Account> getAccounts(){
        return accounts;
    }
    public Set<ClientLoan> getLoans(){ return loans; }
    public Set<Card> getCards(){ return cards;}
}
