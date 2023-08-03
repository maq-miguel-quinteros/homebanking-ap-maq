package com.hb.maq.homebanking;

import com.hb.maq.homebanking.models.Client;
import com.hb.maq.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository){
		return (args -> {
			// creamos registros en la base de datos
			clientRepository.save(new Client("Melba", "Morel", "melba@mindhub.com"));

		});
	}

}
