package com.hb.maq.homebanking;

import com.hb.maq.homebanking.models.*;
import com.hb.maq.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/** @SpringBootApplication es la anotación que aparece en la función main de todo proyecto que definamos con Spring Boot
 * Esta anotación herede el comportamiento de un conjunto de anotaciones:
 * @EnableAutoConfiguration: se encarga de forma inteligente de intentar configurar Spring de forma automática.
 * Busca en el Classpath todas las clases con @Entity y las registra con el proveedor de persistencia que tengamos.
 * @SpringConfiguration: define que el fichero es un fichero de Configuración de Spring.
 * Solo puede haber una de estas anotaciones en la aplicación
 * @ConponentScan: revisa los paquetes actuales y registra de forma automática cualquier @Service @Repository @Controller
 * https://www.arquitecturajava.com/springbootapplication-una-anotacion-clave */
@SpringBootApplication
public class HomebankingApplication {

	/** @Autowired es una anotación utilizada en Spring Boot para habilitar la inyección automática de dependencias.
	 * Permite al contenedor de Spring proporcionar una instancia de una dependencia requerida cuando se crea un bean.
	 * Esta anotación puede utilizarse en campos, constructores y métodos para que Spring proporcione las dependencias automáticamente.
	 * La inyección de dependencias es un patrón de diseño en el que a los objetos se les pasan sus dependencias en lugar de crearlas internamente.
	 * En Spring Boot, @Autowired se utiliza para inyectar objetos en otros objetos.
	 * Esto permite un acoplamiento suelto entre componentes y ayuda a mantener el código más mantenible.
	 * https://gustavopeiretti.com/spring-boot-anotacion-autowired/#:~:text=%40Autowired%20es%20una%20anotaci%C3%B3n%20utilizada,cuando%20se%20crea%20un%20bean */
	@Autowired
	private PasswordEncoder passwordEncoder;


	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	/** Dentro de la infraestructura de Spring tenemos algo llamado Spring Container (contendedor). El núcleo del framework.
	 * El contenedor crea objetos, los une, los configura y administra su ciclo de vida completamente.
	 * Los Beans son objetos que maneja el contendor de spring.
	 * De cierta manera son clases de java, que implementan algo llamado “Dependency Injection”.
	 * Spring puede definir Beans (Objetos) con configuraciones específicas y yo podría inyectarlas en alguna clase que lo necesite.
	 * Spring tiene anotaciones específicas para ello como @Controller que agrega esas características a mi clase
	 * https://es.quora.com/Qu%C3%A9-es-Spring-Beans */
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository,
									  LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
									/** HACEMOS INYECCIÓN DE DEPENDENCIAS DE LAS INTERFACES QUE NECESITAMOS EJ. ClientRepository clientRepository
									 * VAMOS A UTILIZAR EL OBJETO clientRepository PARA APROVECHAR LOS MÉTODOS QUE HEREDA EJ. save() */

		return (args -> {

			/** INDICAMOS QUE LA VARIABLE | ATRIBUTO client01 VA A SER DE TIPO Client MEDIANTE LA SENTENCIA Client client
			 * SE RESERVA UN ESPACIO EN MEMORIA PARA ESA VARIABLE
			 * INSTANCIAMOS LA CLASE | CREAMOS UN OBJETO DE TIPO Client MEDIANTE LA PALABRA RESERVADA new
			 * LLAMAMOS LA CONSTRUCTOR DE LA CLASE Client() PASANDO POR PARÁMETRO (DENTRO DEL PARÉNTESIS) LOS VALORES DEL NUEVO OBJETO
			 * EL CONSTRUCTOR ASIGNA ESTOS VALORES A LOS ATRIBUTOS DE LA CLASE SEGÚN SU DEFINICIÓN */
			Client client01 = new Client("Melba", "Morel", "melba@mindhub.com",
					passwordEncoder.encode("melba"));
					/** UTILIZAMOS EL MÉTODO encode() DEL OBJETO passwordEncoder PARA CODIFICAR LA CONTRASEÑA DEL CLIENTE */

			/** PERSISTIMOS EL CLIENTE EN LA DB PASANDO POR PARÁMETRO EL OBJETO client01 AL MÉTODO save()
			 * MEDIANTE LA INYECCIÓN DE DEPENDENCIAS TENEMOS UN OBJETO DE LA INTERFACE ClientRepository llamado clientRepository
			 * POR HERENCIA DE LA INTERFACE JpaRepository EL OBJETO clientRepository CUENTA CON EL MÉTODO save()
			 * EL MÉTODO save() SE ENCARGA DE HACER LA PERSISTENCIA EN LA DB */
			clientRepository.save(client01);

			/** CREAMOS NUEVOS CLIENTES Y LOS PERSISTIMOS EN LA DB */
			Client client02 = new Client("Miguel", "Quinteros", "ejemplo@ejemplo", passwordEncoder.encode("contraseña"));
			clientRepository.save(client02);
			Client client03 = new Client("Administrador", "HomeBanking", "admin@admin", passwordEncoder.encode("admin"));
			clientRepository.save(client03);


			/** CREAMOS LAS CUENTAS, LAS RELACIONAMOS CON SU CLIENTE Y LAS PERSISTIMOS EN LA DB */
			Account account01 = new Account("VIN001",
					LocalDate.now(), 5000);
					/** MEDIANTE EL MÉTODO now() DEL OBJETO LocalDate PASAMOS LA FECHA ACTUAL (LA DEL MOMENTO DE EJECUCIÓN) */

			/** MEDIANTE EL MÉTODO addAccounts() DEL OBJETO client01 RELACIONAMOS LA CUENTA account01 A ESE OBJETO */
			client01.addAccounts(account01);
			accountRepository.save(account01);
			Account account02 = new Account("VIN002",
					LocalDate.now().plusDays(1), 7000);
									/** MEDIANTE EL MÉTODO plusDays() SUMAMOS DIAS A LA FECHA ACTUAL
									 * QUE NOS DEVUELVE EL MÉTODO now() DEL OBJETO LocalDate */
			client01.addAccounts(account02);
			accountRepository.save(account02);
			Account account03 = new Account("VIN001", LocalDate.now(), 5000);
			client02.addAccounts(account03);
			accountRepository.save(account03);
			Account account04 = new Account("VIN002", LocalDate.now().plusDays(1), 7000);
			client02.addAccounts(account04);
			accountRepository.save(account04);


			/** CREAMOS LAS TRANSACCIONES, LAS RELACIONAMOS CON SU CUENTA Y LAS PERSISTIMOS EN LA DB */
			Transaction transaction01 = new Transaction(TransactionType.CREDIT, 15000, "Transferencia desde cuenta propia",
					LocalDateTime.now());
					/** MEDIANTE EL MÉTODO now() DEL OBJETO LocalDateTime PASAMOS LA FECHA Y HORA ACTUAL (LA DEL MOMENTO DE EJECUCIÓN) */
			account01.addTransaction(transaction01);
			transactionRepository.save(transaction01);


			/**  CREAMOS LOS PRESTAMOS Y LOS PERSISTIMOS EN LA DB */
			Loan loan01 = new Loan("Hipotecario", 500000.0,
					List.of(12,24,36,48,60));
					/** MEDIANTE EL MÉTODO of() DEL OBJETO List PASAMOS UNA LISTA DE ENTEROS
					 * QUE REPRESENTAN EL ATRIBUTO payments DE LA CLASE */
			loanRepository.save(loan01);
			Loan loan02 = new Loan("Personal", 100000.0, List.of(6,12,24));
			loanRepository.save(loan02);
			Loan loan03 = new Loan("Automotriz", 300000.0, List.of(6,12,24,36));
			loanRepository.save(loan03);


			/** CREAMOS LAS ENTIDADES ClientLoan INTERMEDIA ENTRE EL CLIENTE Y EL PRÉSTAMO Y LAS PERSISTIMOS EN LA DB*/
			ClientLoan clientLoan01 = new ClientLoan( 400000.0, 60, client01, loan01);
			clientLoanRepository.save(clientLoan01);
			ClientLoan clientLoan02 = new ClientLoan(50000.0, 12, client01, loan02);
			clientLoanRepository.save(clientLoan02);
			ClientLoan clientLoan03 = new ClientLoan(100000.0, 24, client02, loan02);
			clientLoanRepository.save(clientLoan03);
			ClientLoan clientLoan04 = new ClientLoan(200000.0, 36, client02 ,loan03);
			clientLoanRepository.save(clientLoan04);


			/** CREAMOS LAS TARJETAS, LAS RELACIONAMOS CON SU CLIENTE Y LAS PERSISTIMOS EN LA DB */
			Card card01 = new Card((client01.getFirstName() + " " + client01.getLastName()),
					/** Los enums, llamados también enumeraciones o listado específico, se refieren a la herramienta
					 * que permite representar conjuntos de constantes con un nombre.
					 * Estas enumeraciones se utilizan cuando se tiene conocimiento completo
					 * con respecto de los valores posibles que puede tomar */
					CardType.DEBIT, CardColor.GOLD, "1234987645670001", 977,
					/** MEDIANTE LOS enum CardType Y CardColor PASAMOS LOS VALORES DEBIT Y GOLD
					 * AL CONSTRUCTOR DE LA CLASE Card */
					LocalDate.now(), LocalDate.now().plusDays(1825));
			client01.addCards(card01);
			cardRepository.save(card01);
			Card card02 = new Card((client01.getFirstName() + " " + client01.getLastName()),
					CardType.CREDIT, CardColor.TITANIUM, "9874123445670001", 917,
					LocalDate.now(), LocalDate.now().plusDays(1825));
			client01.addCards(card02);
			cardRepository.save(card02);
			Card card03 = new Card((client02.getFirstName() + " " + client02.getLastName()),
					CardType.CREDIT, CardColor.SILVER, "1234987645670002", 951,
					LocalDate.now(), LocalDate.now().plusDays(1825));
			client02.addCards(card03);
			cardRepository.save(card03);
		});
	}
}