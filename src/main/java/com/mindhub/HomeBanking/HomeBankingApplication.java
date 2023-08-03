package com.mindhub.HomeBanking;

import com.mindhub.HomeBanking.models.*;
import com.mindhub.HomeBanking.repositories.*;
import com.mindhub.HomeBanking.utils.LoanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@SpringBootApplication
public class HomeBankingApplication {
	@Autowired
    private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(HomeBankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository client, AccountRepository account, TransactionRepository transaction, LoanRepository loan, ClientLoanRepository clientLoan, CardRepository card){
		return(args) -> {
			Client client1 = new Client("Melba","Morel","melba@mindhub.com", passwordEncoder.encode("melba"));
			client.save(client1);
			Card card1 = new Card(client1.getFirstName() + " " + client1.getLastName(),CardType.DEBIT,CardColor.GOLD,"4017" + " " + "6313" + " " + "8032" + " " + "6087",436,LocalDate.now(), LocalDate.now().plusYears(5),true);
			Card card2 = new Card(client1.getFirstName() + " " + client1.getLastName(),CardType.CREDIT,CardColor.TITANIUM,"6313" + " " + "4017" + " " + "8092" + " " + "6787",476,LocalDate.now(),LocalDate.now().plusYears(5),true);
			Card card3 = new Card(client1.getFirstName() + " " + client1.getLastName(),CardType.CREDIT,CardColor.SILVER,"6243" + " " + "4787" + " " + "8055" + " " + "3487",686,LocalDate.now(),LocalDate.now().minusYears(5).minusDays(1),true);
			Account account1 = new Account("VIN-001", LocalDateTime.now(),5000,true,AccountType.CURRENT);
			Account account2 = new Account("VIN-002", LocalDateTime.now().plusDays(1),7500,true, AccountType.SAVING);

			client1.addAccount(account1);
			client1.addAccount(account2);

			client1.addCard((card1));
			client1.addCard((card2));
			client1.addCard((card3));

			card.save(card1);
			card.save(card2);
			card.save(card3);

			Transaction transaction1 = new Transaction(TransactionType.CREDIT,3000,"Credit of Melba",LocalDateTime.now(), account1.getBalance(), true);
			Transaction transaction2 = new Transaction(TransactionType.DEBIT,2000,"Debit of Melba",LocalDateTime.now(),account1.getBalance(),true);

			account.save(account1);
			account.save(account2);

			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);

			transaction.save(transaction1);
			transaction.save(transaction2);

			Transaction transaction3 = new Transaction(TransactionType.CREDIT,3000,"Credit of Melba",LocalDateTime.now(),account2.getBalance(),true);
			Transaction transaction4 = new Transaction(TransactionType.DEBIT,2000,"Debit of Melba",LocalDateTime.now(),account2.getBalance(),true);

			account2.addTransaction(transaction3);
			account2.addTransaction(transaction4);

			transaction.save(transaction3);
			transaction.save(transaction4);

			Client client3 = new Client("Luisa", "Mendoza","luisafmv321@gmail.com", passwordEncoder.encode("luisa"));
			client.save(client3);
			Loan loan1 = new Loan("Mortgage",500000, Set.of(12,24,36,48,60),1.2);
			Loan loan2 = new Loan("Personal",100000, Set.of(6,12,24),1.05);
			Loan loan3 = new Loan("Automotive",300000, Set.of(6,12,24,36),1.15);

			loan.save(loan1);
			loan.save(loan2);
			loan.save(loan3);

			ClientLoan clientLoan1 = new ClientLoan(400000,60, 400000 * LoanUtils.calculateInterest(60,loan1.getInterest()));
			ClientLoan clientLoan2 = new ClientLoan(50000,12, 50000 * LoanUtils.calculateInterest(12,loan2.getInterest()));

			ClientLoan clientLoan3 = new ClientLoan(100000,24, 100000 * LoanUtils.calculateInterest(24,loan3.getInterest()));
			ClientLoan clientLoan4 = new ClientLoan(200000,36, 200000 * LoanUtils.calculateInterest(36,loan3.getInterest()));

			client1.addClientLoan(clientLoan1);
			loan1.addClientLoan(clientLoan1);
			clientLoan.save(clientLoan1);

			client1.addClientLoan(clientLoan2);
			loan2.addClientLoan(clientLoan2);
			clientLoan.save(clientLoan2);

		};
	}
}
