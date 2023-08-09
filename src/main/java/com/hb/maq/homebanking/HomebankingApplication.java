package com.hb.maq.homebanking;

import com.hb.maq.homebanking.models.Account;
import com.hb.maq.homebanking.models.Client;
import com.hb.maq.homebanking.models.Transaction;
import com.hb.maq.homebanking.models.TransactionType;
import com.hb.maq.homebanking.repositories.AccountRepository;
import com.hb.maq.homebanking.repositories.ClientRepository;
import com.hb.maq.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
		return (args -> {
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

		});
	}

}
