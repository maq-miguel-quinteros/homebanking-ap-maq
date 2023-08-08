package com.hb.maq.homebanking;

import com.hb.maq.homebanking.models.Account;
import com.hb.maq.homebanking.models.Client;
import com.hb.maq.homebanking.repositories.AccountRepository;
import com.hb.maq.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository){
		return (args -> {
			// creamos el cliente de prueba
			Client client01 = new Client("Melba", "Morel", "melba@mindhub.com");
			clientRepository.save(client01);

			// creamos las cuentas para el cliente de pruebas y las relacionamos con el cliente
			Account account01 = new Account("VIN001", LocalDate.now(), 5000);
			client01.addAccounts(account01);
			accountRepository.save(account01);

			Account account02 = new Account("VIN002", LocalDate.now().plusDays(1), 5000);
			client01.addAccounts(account02);
			accountRepository.save(account02);

			Client client02 = new Client("Miguel", "Quinteros", "ejemplo@ejemplo");
			clientRepository.save(client02);

			// creamos las cuentas para el cliente de pruebas y las relacionamos con el cliente
			Account account03 = new Account("VIN001", LocalDate.now(), 5000);
			client01.addAccounts(account03);
			accountRepository.save(account03);

			Account account04 = new Account("VIN002", LocalDate.now().plusDays(1), 5000);
			client01.addAccounts(account04);
			accountRepository.save(account04);

		});
	}

}
