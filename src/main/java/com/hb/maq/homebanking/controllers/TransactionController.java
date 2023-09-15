package com.hb.maq.homebanking.controllers;

import com.hb.maq.homebanking.models.Account;
import com.hb.maq.homebanking.models.Client;
import com.hb.maq.homebanking.models.Transaction;
import com.hb.maq.homebanking.models.TransactionType;
import com.hb.maq.homebanking.repositories.AccountRepository;
import com.hb.maq.homebanking.repositories.ClientRepository;
import com.hb.maq.homebanking.repositories.TransactionRepository;
import com.hb.maq.homebanking.services.AccountService;
import com.hb.maq.homebanking.services.ClientService;
import com.hb.maq.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {

    /** INYECCIÓN DE DEPENDENCIAS */
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    /** CREATE */
    @Transactional
    @PostMapping(value = "/transactions")
    public ResponseEntity<Object> createTransactions(
            @RequestParam String fromAccountNumber, @RequestParam  String toAccountNumber,
            @RequestParam Double amount, @RequestParam String description, Authentication authentication){

        /** 403 forbidden, si el monto o la descripción están vacíos
         * 403 forbidden, si alguno de los números de cuenta están vacíos */
        if (fromAccountNumber.isEmpty() || toAccountNumber.isEmpty() || amount.isNaN() || description.isEmpty()){
            return new ResponseEntity<>("Faltan ingresar datos para realizar la transacción", HttpStatus.FORBIDDEN);
        }

        Account fromAccount = accountService.findByNumber(fromAccountNumber);
        /** 403 forbidden, si la cuenta de origen no existe */
        if ( fromAccount == null ){
            return new ResponseEntity<>("El número de cuenta orígen no existe", HttpStatus.FORBIDDEN);
        }

        Account toAccount = accountService.findByNumber(toAccountNumber);
        /** 403 forbidden, si la cuenta de destino no existe */
        if ( toAccount == null ){
            return new ResponseEntity<>("El número de cuenta destino no existe", HttpStatus.FORBIDDEN);
        }

        /** 403 forbidden, si la cuenta de origen es la misma que la destino */
        if (fromAccount.equals(toAccount)){
            return new ResponseEntity<>("El número de cuenta origen y destino es el mismo", HttpStatus.FORBIDDEN);
        }

        Client client = clientService.findByEmail(authentication.getName());
        /** 403 forbidden, si la cuenta de origen no pertenece al cliente autenticado */
        if ( !client.getAccounts().contains(fromAccount) ){
            return new ResponseEntity<>("El número de cuenta origen no corresponde al cliente", HttpStatus.FORBIDDEN);
        }

        /** 403 forbidden, si el cliente no tiene fondos */
        if (fromAccount.getBalance() < amount ){
            return new ResponseEntity<>("No cuenta con los fondos suficientes para realizar la transacción", HttpStatus.FORBIDDEN);
        }

        Transaction debit = new Transaction(TransactionType.DEBIT, amount * -1, description + " " + fromAccount.getNumber() ,
                LocalDateTime.now(), fromAccount.getBalance() - amount );
        fromAccount.addTransaction(debit);
        transactionService.save(debit);
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        accountService.save(fromAccount);

        Transaction credit = new Transaction(TransactionType.CREDIT, amount, description + " " + toAccount.getNumber(),
                LocalDateTime.now(), toAccount.getBalance() + amount );
        toAccount.addTransaction(credit);
        transactionService.save(credit);
        toAccount.setBalance(toAccount.getBalance() + amount);
        accountService.save(toAccount);

        return new ResponseEntity<>("Transacción creada correctamente", HttpStatus.CREATED);
    }
}