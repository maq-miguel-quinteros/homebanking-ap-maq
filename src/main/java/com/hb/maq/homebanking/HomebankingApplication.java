package com.hb.maq.homebanking;

import com.hb.maq.homebanking.models.*;
import com.hb.maq.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository,
									  LoanRepository loanRepository, ClientLoanRepository clientLoanRepository){
		return (args -> {
			/**  CREAMOS LOS PRESTAMOS */
			Loan loan01 = new Loan("Hipotecario", 500000.0, List.of(12,24,36,48,60));
			loanRepository.save(loan01);

			Loan loan02 = new Loan("Personal", 100000.0, List.of(6,12,24));
			loanRepository.save(loan02);

			Loan loan03 = new Loan("Automotriz", 300000.0, List.of(6,12,24,36));
			loanRepository.save(loan03);


			/** CREO AL CLIENTE DE PRUEBA Y LO GUARDO EN LA DB MEDIANTE LA INTERFACE */
			Client client01 = new Client("Melba", "Morel", "melba@mindhub.com");
			clientRepository.save(client01);

			/** CREO LAS CUENTAS DE PRUEBA, RELACIONO LAS CUENTAS CON EL CLIENTE Y LAS GUARDO EN LA DB */
			Account account01 = new Account("VIN001", LocalDate.now(), 5000);
			client01.addAccounts(account01);
			accountRepository.save(account01);

			Account account02 = new Account("VIN002", LocalDate.now().plusDays(1), 7000);
			client01.addAccounts(account02);
			accountRepository.save(account02);

			/** CREO LAS TRANSACCIONES, LAS RELACIONO CON LA CUENTA Y LAS GUARDO EN LA DB */
			Transaction transaction01 = new Transaction(TransactionType.CREDIT, 15000, "Transferencia desde cuenta propia", LocalDateTime.now());
			account01.addTransaction(transaction01);
			transactionRepository.save(transaction01);



			Client client02 = new Client("Miguel", "Quinteros", "ejemplo@ejemplo");
			clientRepository.save(client02);

			// creamos las cuentas para el cliente de pruebas y las relacionamos con el cliente
			Account account03 = new Account("VIN001", LocalDate.now(), 5000);
			client02.addAccounts(account03);
			accountRepository.save(account03);

			Account account04 = new Account("VIN002", LocalDate.now().plusDays(1), 7000);
			client02.addAccounts(account04);
			accountRepository.save(account04);

			/** CREAMOS LAS ENTIDADES CLIENTLOAN */
			ClientLoan clientLoan01 = new ClientLoan( 400000.0, 60, client01, loan01);
			clientLoanRepository.save(clientLoan01);
			ClientLoan clientLoan02 = new ClientLoan(50000.0, 12, client01, loan02);
			clientLoanRepository.save(clientLoan02);

			ClientLoan clientLoan03 = new ClientLoan(100000.0, 24, client02, loan02);
			clientLoanRepository.save(clientLoan03);
			ClientLoan clientLoan04 = new ClientLoan(200000.0, 36, client02 ,loan03);
			clientLoanRepository.save(clientLoan04);


		});
	}

}
