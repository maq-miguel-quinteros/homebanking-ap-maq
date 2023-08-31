package com.hb.maq.homebanking.controllers;

import com.hb.maq.homebanking.dtos.AccountDTO;
import com.hb.maq.homebanking.models.Account;
import com.hb.maq.homebanking.models.Client;
import com.hb.maq.homebanking.repositories.AccountRepository;
import com.hb.maq.homebanking.repositories.ClientRepository;
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
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    /** ####### CRUD: CREATE | READ | UPDATE | DELETE ####### */
    /** CREATE */
    @RequestMapping(value = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount(Authentication authentication){

        Client client = clientRepository.findByEmail(authentication.getName());

        if (client.getAccounts().size() < 3 ){

            Account account = new Account("VIN-" + ((int)(Math.random() * 99999999 + 1)),
                    LocalDate.now(), 0);

            while ( accountRepository.findByNumber(account.getNumber()) != null ){
                account.setNumber("VIN-" + ((int)(Math.random() * 99999999 + 1)));
            }

            client.addAccounts(account);
            accountRepository.save(account);

            return new ResponseEntity<>(HttpStatus.CREATED);

        } else {
            return new ResponseEntity<>("Solo se pueden generar 3 cuentas por cliente", HttpStatus.FORBIDDEN);
        }
    }

    /** READ */
    @RequestMapping(value = "/clients/current/accounts")
    public List<AccountDTO> readAccounts(Authentication authentication){

        Client client = clientRepository.findByEmail(authentication.getName());

        return accountRepository.findByClient(client)
                .stream().map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }



    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll()
                .stream().map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return accountRepository.findById(id)
                .map(account -> new AccountDTO(account))
                .orElse(null);
    }
}
