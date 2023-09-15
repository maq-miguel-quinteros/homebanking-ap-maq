package com.hb.maq.homebanking.controllers;

import com.hb.maq.homebanking.dtos.AccountDTO;
import com.hb.maq.homebanking.models.Account;
import com.hb.maq.homebanking.models.Client;
import com.hb.maq.homebanking.models.Transaction;
import com.hb.maq.homebanking.repositories.AccountRepository;
import com.hb.maq.homebanking.repositories.ClientRepository;
import com.hb.maq.homebanking.services.AccountService;
import com.hb.maq.homebanking.services.ClientService;
import com.hb.maq.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {

    /** INYECCIÓN DE DEPENDENCIAS */
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private TransactionService transactionService;

    /** CREATE ACCOUNT */
    @PostMapping(value = "/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication){

        Client client = clientService.findByEmail(authentication.getName());

        if (client.getAccounts().size() < 3 ){

            Account account = new Account("VIN-" + ((int)(Math.random() * 99999999 + 1)),
                    LocalDate.now(), 0.0);

            while ( accountService.findByNumber(account.getNumber()) != null ){
                account.setNumber("VIN-" + ((int)(Math.random() * 99999999 + 1)));
            }

            client.addAccounts(account);
            accountService.save(account);

            return new ResponseEntity<>(HttpStatus.CREATED);

        } else {
            return new ResponseEntity<>("Solo se pueden generar 3 cuentas por cliente", HttpStatus.FORBIDDEN);
        }
    }

    /** READ ACCOUNTS CURRENT CLIENT */
    @GetMapping(value = "/clients/current/accounts")
    public List<AccountDTO> readAccounts(Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        return accountService.findByClientToListAccountDTO(client);
    }
    @GetMapping("/accounts/{id}")
    public AccountDTO readAccount(@PathVariable Long id){
        return accountService.findByIdToAccountDTO(id);
    }




    /** Agregar botón para eliminar cuentas, se deben eliminar las transacciones de la cuenta. */
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable Long id){
        Account account = accountService.findById(id);
        if ( account != null){
            List<Transaction> transactions = transactionService.findAllByAccount(account);
            if (transactions != null){
                for (Transaction transaction : transactions ) {
                    transactionService.deleteById(transaction.getId());
                }
            }
            accountService.deleteById(account.getId());
            return new ResponseEntity<>(HttpStatus.PROCESSING);
        } else {
            return new ResponseEntity<>("La cuenta que desea borrar no existe", HttpStatus.FORBIDDEN);
        }
    }

    /*
    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll()
                .stream().map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }
    */




}
