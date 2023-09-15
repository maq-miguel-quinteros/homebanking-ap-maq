package com.hb.maq.homebanking.controllers;

import com.hb.maq.homebanking.dtos.LoanApplicationDTO;
import com.hb.maq.homebanking.dtos.LoanDTO;
import com.hb.maq.homebanking.models.*;
import com.hb.maq.homebanking.repositories.*;
import com.hb.maq.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {


    @Autowired
    private LoanService loanService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientLoanService clientLoanService;
    @Autowired
    private TransactionService transactionService;


    /** ####### CRUD: CREATE | READ | UPDATE | DELETE ####### */
    /** CREATE */
    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createLoan(@RequestBody LoanApplicationDTO loanApplicationDTO,
                                     Authentication authentication){

        /** 403 forbidden, si alguno de los datos no es válido */
        if (loanApplicationDTO.getLoanId() == null || loanApplicationDTO.getAmount() == null ||
            loanApplicationDTO.getPayments() == null || loanApplicationDTO.getToAccountNumber() == null){
                return new ResponseEntity<>("Los datos ingresados no son válidos", HttpStatus.FORBIDDEN);
            }
        if (loanApplicationDTO.getAmount() == 0){
            return new ResponseEntity<>("El monto solicitado no puede ser 0", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getPayments() == 0){
            return new ResponseEntity<>("Las cuotas seleccionadas no pueden ser 0", HttpStatus.FORBIDDEN);
        }

        Loan loan = loanService.findById(loanApplicationDTO.getLoanId());

        /** 403 forbidden, si el préstamo no existe */
        if (loan == null){
            return new ResponseEntity<>("El prestamo solicitado no existe", HttpStatus.FORBIDDEN);
        }

        /** 403 forbidden, si el monto solicitado supera el monto máximo permitido del préstamo solicitado */
        if (loanApplicationDTO.getAmount() > loan.getMaxAmount()){
            return new ResponseEntity<>("El monto solicitado supera el máximo permitido para el tipo de préstamo", HttpStatus.FORBIDDEN);
        }

        /** 403 forbidden, si la cantidad de cuotas no está disponible para el préstamo solicitado */
        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("La cantidad de cuotas solicitada no está disponible par el tipo de préstamo", HttpStatus.FORBIDDEN);
        }

        Account toAccount = accountService.findByNumber(loanApplicationDTO.getToAccountNumber());

        /** 403 forbidden, si la cuenta de destino no existe */
        if (toAccount == null){
            return new ResponseEntity<>("La cuenta de destino no existe", HttpStatus.FORBIDDEN);
        }

        Client client = clientService.findByEmail(authentication.getName());

        /** 403 forbidden, si la cuenta de destino no pertenece al cliente autenticado */
        if (!client.getAccounts().contains(toAccount)){
            return new ResponseEntity<>("La cuenta destino no pertenece al cliente", HttpStatus.FORBIDDEN);
        }

        Double loanAmount = loanApplicationDTO.getAmount() + (loanApplicationDTO.getAmount() * 0.2);

        ClientLoan clientLoan = new ClientLoan(loanAmount, loanApplicationDTO.getPayments());
        client.addClientLoan(clientLoan);
        loan.addClientLoan(clientLoan);
        clientLoanService.save(clientLoan);

        Transaction credit = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(),
                "New loan " + loan.getName() + " to account " + toAccount.getNumber() + ". Loan approved",
                LocalDateTime.now(), toAccount.getBalance() + loanApplicationDTO.getAmount() );
        toAccount.addTransaction(credit);
        transactionService.save(credit);
        toAccount.setBalance(toAccount.getBalance() + loanApplicationDTO.getAmount());
        accountService.save(toAccount);

        return new ResponseEntity<>("OK",HttpStatus.CREATED);

        }

    /** READ */
    @GetMapping("/loans")
    public List<LoanDTO> readLoans(){
        return loanService.findAllToListLoanDTO();
    }
}
