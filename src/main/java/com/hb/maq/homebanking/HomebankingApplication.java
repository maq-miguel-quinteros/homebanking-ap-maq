package com.hb.maq.homebanking;

import com.hb.maq.homebanking.models.*;
import com.hb.maq.homebanking.services.*;
import com.hb.maq.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@SpringBootApplication
public class HomebankingApplication {

//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	@Autowired
//	private ClientService clientService;
//	@Autowired
//	private AccountService accountService;
//	@Autowired
//	private TransactionService transactionService;
//	@Autowired
//	private LoanService loanService;
//	@Autowired
//	private ClientLoanService clientLoanService;
//	@Autowired
//	private CardService cardService;



	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(){
		return (args -> {

//			/** CREAMOS NUEVOS CLIENTES Y LOS PERSISTIMOS EN LA DB */
//			Client client01 = new Client("Melba", "Morel", "melba@mindhub.com",
//					passwordEncoder.encode("melba"));
//			clientService.save(client01);
//			Client client02 = new Client("Miguel", "Quinteros", "ejemplo@ejemplo",
//					passwordEncoder.encode("contraseña"));
//			clientService.save(client02);
//			Client client03 = new Client("Administrador", "HomeBanking", "admin@admin",
//					passwordEncoder.encode("admin"));
//			clientService.save(client03);
//			Client client04 = new Client("Bruce", "Wayne","brucewayne@lexcorp.com",
//					passwordEncoder.encode("iambatman89"));
//			clientService.save(client04);
//			Client client05 = new Client("Mary Jane", "Watson", "mjwatson@uolsinectis.com",
//					passwordEncoder.encode("iloveharry"));
//			clientService.save(client05);
//
//
//			/** CREAMOS LAS CUENTAS, LAS RELACIONAMOS CON SU CLIENTE Y LAS PERSISTIMOS EN LA DB */
//			Account account01A = new Account("VIN-" + ((int)(Math.random() * 99999999 + 1)),
//					LocalDate.now(), 5000.0);
//			client01.addAccounts(account01A);
//			accountService.save(account01A);
//			Account account01B = new Account("VIN-" + ((int)(Math.random() * 99999999 + 1)),
//					LocalDate.now().plusDays(1), 7000.0);
//			client01.addAccounts(account01B);
//			accountService.save(account01B);
//
//			Account account02 = new Account("VIN-" + ((int)(Math.random() * 99999999 + 1)),
//					LocalDate.now(), 5000.0);
//			client02.addAccounts(account02);
//			accountService.save(account02);
//
//			Account account03 = new Account("VIN-" + ((int)(Math.random() * 99999999 + 1)),
//					LocalDate.now().plusDays(1), 7000.0);
//			client02.addAccounts(account03);
//			accountService.save(account03);
//
//			Account account04A = new Account("VIN-" + ((int)(Math.random() * 99999999 + 1)),
//					LocalDate.now(), 5000000000.0);
//			client04.addAccounts(account04A);
//			accountService.save(account04A);
//			Account account04B = new Account("VIN-" + ((int)(Math.random() * 99999999 + 1)),
//					LocalDate.now(), 9000000000000.0);
//			client04.addAccounts(account04B);
//			accountService.save(account04B);
//
//			Account account05 = new Account("VIN-" + ((int)(Math.random() * 99999999 + 1)),
//					LocalDate.now(), 1000.0);
//			client05.addAccounts(account05);
//			accountService.save(account05);
//
//
//			/** CREAMOS LAS TRANSACCIONES, LAS RELACIONAMOS CON SU CUENTA Y LAS PERSISTIMOS EN LA DB */
//			Transaction transaction01A = new Transaction(TransactionType.CREDIT, 15000, "Transferencia desde cuenta no propia",
//					LocalDateTime.now(), account01A.getBalance());
//			account01A.addTransaction(transaction01A);
//			transactionService.save(transaction01A);
//			Transaction transaction01B = new Transaction(TransactionType.DEBIT, -500, "Pago de servicios",
//					LocalDateTime.now(), account01A.getBalance());
//			account01A.addTransaction(transaction01B);
//			transactionService.save(transaction01B);
//			Transaction transaction01C = new Transaction(TransactionType.DEBIT, -1000.2, "Compra en casa de ropa",
//					LocalDateTime.now(), account01A.getBalance());
//			account01A.addTransaction(transaction01C);
//			transactionService.save(transaction01C);
//
//			Transaction transaction04A = new Transaction(TransactionType.DEBIT, -200000000.5, "Compra de todo Warner",
//					LocalDateTime.now(), account04A.getBalance());
//			account04A.addTransaction(transaction04A);
//			transactionService.save(transaction04A);
//			Transaction transaction04B = new Transaction(TransactionType.DEBIT, -50000, "Pago de aguinaldo a Alfred",
//					LocalDateTime.now(), account04A.getBalance());
//			account04A.addTransaction(transaction04B);
//			transactionService.save(transaction04B);
//			Transaction transaction04C = new Transaction(TransactionType.CREDIT, 100000000, "Venta de dollar oficial al arbolito",
//					LocalDateTime.now(), account04A.getBalance());
//			account04A.addTransaction(transaction04C);
//			transactionService.save(transaction04C);
//
//			Transaction transaction04D = new Transaction(TransactionType.DEBIT, -20000.5, "Compra de papas fritas hechas en francia",
//					LocalDateTime.now(), account04B.getBalance());
//			account04B.addTransaction(transaction04D);
//			transactionService.save(transaction04D);
//			Transaction transaction04E = new Transaction(TransactionType.CREDIT, 50000000, "Ganancia por venta de colchones usados",
//					LocalDateTime.now(), account04B.getBalance());
//			account04B.addTransaction(transaction04E);
//			transactionService.save(transaction04E);
//			Transaction transaction04F = new Transaction(TransactionType.DEBIT, 10000, "Pago del instituto de Robin",
//					LocalDateTime.now(), account04B.getBalance());
//			account04B.addTransaction(transaction04F);
//			transactionService.save(transaction04F);
//
//
//			/**  CREAMOS LOS PRESTAMOS Y LOS PERSISTIMOS EN LA DB */
//			Loan loan01 = new Loan("Hipotecario", 500000.0,
//					List.of(12,24,36,48,60));
//			loanService.save(loan01);
//			Loan loan02 = new Loan("Personal", 100000.0,
//					List.of(6,12,24));
//			loanService.save(loan02);
//			Loan loan03 = new Loan("Automotriz", 300000.0,
//					List.of(6,12,24,36));
//			loanService.save(loan03);
//
//
//			/** CREAMOS LAS ENTIDADES ClientLoan INTERMEDIA ENTRE EL CLIENTE Y EL PRÉSTAMO, LAS RELACIONAMOS
//			 * CON SU CLIENTE Y CON SU PRESTAMOS RESPECTIVOS Y LAS PERSISTIMOS EN LA DB*/
//			ClientLoan clientLoan01 = new ClientLoan( 400000.0, 60);
//			client01.addClientLoan(clientLoan01);
//			loan01.addClientLoan(clientLoan01);
//			clientLoanService.save(clientLoan01);
//			ClientLoan clientLoan02 = new ClientLoan(50000.0, 12);
//			client01.addClientLoan(clientLoan02);
//			loan02.addClientLoan(clientLoan02);
//			clientLoanService.save(clientLoan02);
//			ClientLoan clientLoan03 = new ClientLoan(100000.0, 24);
//			client02.addClientLoan(clientLoan03);
//			loan02.addClientLoan(clientLoan03);
//			clientLoanService.save(clientLoan03);
//			ClientLoan clientLoan04 = new ClientLoan(200000.0, 36);
//			client02.addClientLoan(clientLoan04);
//			loan03.addClientLoan(clientLoan04);
//			clientLoanService.save(clientLoan04);
//
//
//			/** CREAMOS LAS TARJETAS, LAS RELACIONAMOS CON SU CLIENTE Y LAS PERSISTIMOS EN LA DB */
//			Card card01 = new Card(
//					(client01.getFirstName() + " " + client01.getLastName()),
//					CardType.DEBIT, CardColor.GOLD, CardUtils.getCardNumber(), CardUtils.getCardCvv(),
//					LocalDate.now(), LocalDate.now().plusYears(-5));
//			client01.addCards(card01);
//			cardService.save(card01);
//			Card card02 = new Card(
//					(client01.getFirstName() + " " + client01.getLastName()),
//					CardType.CREDIT, CardColor.TITANIUM, CardUtils.getCardNumber(), CardUtils.getCardCvv(),
//					LocalDate.now(), LocalDate.now().plusYears(5));
//			client01.addCards(card02);
//			cardService.save(card02);
//			Card card03 = new Card(
//					(client02.getFirstName() + " " + client02.getLastName()),
//					CardType.CREDIT, CardColor.SILVER, CardUtils.getCardNumber(), CardUtils.getCardCvv(),
//					LocalDate.now(), LocalDate.now().plusYears(5));
//			client02.addCards(card03);
//			cardService.save(card03);

		});
	}
}