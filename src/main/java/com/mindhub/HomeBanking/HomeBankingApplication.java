package com.mindhub.HomeBanking;

import com.mindhub.HomeBanking.models.*;
import com.mindhub.HomeBanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class HomeBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeBankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository client, AccountRepository account, TransactionRepository transaction, LoanRepository loan, ClientLoanRepository clientLoan, CardRepository card){
		return(args) -> {
			Client client1 = new Client("Melba","Morel","melba@mindhub.com");
			client.save(client1);
			Card card1 = new Card(client1.getFirstName() + " " + client1.getLastName(),CardType.DEBIT,CardColor.GOLD,"4017-6313-8032-6087",436,LocalDate.now(), LocalDate.now().plusYears(5));
			Card card2 = new Card(client1.getFirstName() + " " + client1.getLastName(),CardType.CREDIT,CardColor.TITANIUM,LocalDate.now(),LocalDate.now().plusYears(5));
			Account account1 = new Account("VIN001", LocalDateTime.now(),5000);
			Account account2 = new Account("VIN002", LocalDateTime.now().plusDays(1),7500);

			client1.addAccount(account1);
			client1.addAccount(account2);
			client1.addCard((card1));
			client1.addCard((card2));

			card.save(card1);
			card.save(card2);

			Transaction transaction1 = new Transaction(TransactionType.CREDIT,3000,"Credit of Melba",LocalDateTime.now());
			Transaction transaction2 = new Transaction(TransactionType.DEBIT,2000,"Debit of Melba",LocalDateTime.now());

			account.save(account1);
			account.save(account2);

			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);

			transaction.save(transaction1);
			transaction.save(transaction2);

			Transaction transaction3 = new Transaction(TransactionType.CREDIT,3000,"Credit of Melba",LocalDateTime.now());
			Transaction transaction4 = new Transaction(TransactionType.DEBIT,2000,"Debit of Melba",LocalDateTime.now());

			account2.addTransaction(transaction3);
			account2.addTransaction(transaction4);

			transaction.save(transaction3);
			transaction.save(transaction4);

			Client client2 = new Client("Nicoll","Laguna","Nicoll@gmail.com");
			client.save(client2);

			Card card3 = new Card(client2.getFirstName() + " " + client2.getLastName(),CardType.CREDIT,CardColor.SILVER,"5287-0668-8884-8458",315,LocalDate.now(), LocalDate.now().plusYears(5));

			Account account3 = new Account("VIN003", LocalDateTime.now(),50000);
			Account account4 = new Account("VIN004", LocalDateTime.now().plusDays(4),100000);

			client2.addAccount(account3);
			client2.addAccount(account4);
			client2.addCard(card3);

			card.save(card3);

			Transaction transaction5 = new Transaction(TransactionType.CREDIT,10000,"Credit of Nicoll",LocalDateTime.now());
			Transaction transaction6 = new Transaction(TransactionType.DEBIT,4570,"Debit of Nicoll",LocalDateTime.now());
			Transaction transaction7 = new Transaction(TransactionType.DEBIT,1504,"Debit of Nicoll",LocalDateTime.now());

			account.save(account3);
			account.save(account4);

			account3.addTransaction(transaction5);
			account3.addTransaction(transaction6);
			account3.addTransaction(transaction7);

			transaction.save(transaction5);
			transaction.save(transaction6);
			transaction.save(transaction7);

			Transaction transaction8 = new Transaction(TransactionType.CREDIT,78000,"Credit of Nicoll",LocalDateTime.now());
			Transaction transaction9 = new Transaction(TransactionType.DEBIT,12000,"Debit of Nicoll",LocalDateTime.now());
			Transaction transaction10 = new Transaction(TransactionType.CREDIT,7300,"Credit of Nicoll",LocalDateTime.now());

			account4.addTransaction(transaction8);
			account4.addTransaction(transaction9);
			account4.addTransaction(transaction10);

			transaction.save(transaction8);
			transaction.save(transaction9);
			transaction.save(transaction10);

			Loan loan1 = new Loan("Mortgage",500000, Set.of(12,24,36,48,60));
			Loan loan2 = new Loan("Personal",100000, Set.of(6,12,24));
			Loan loan3 = new Loan("Automotive",300000, Set.of(6,12,24,36));

			loan.save(loan1);
			loan.save(loan2);
			loan.save(loan3);

			ClientLoan clientLoan1 = new ClientLoan(400000,60);
			ClientLoan clientLoan2 = new ClientLoan(50000,12);

			ClientLoan clientLoan3 = new ClientLoan(100000,24);
			ClientLoan clientLoan4 = new ClientLoan(200000,36);

			client1.addClientLoan(clientLoan1);
			loan1.addClientLoan(clientLoan1);
			clientLoan.save(clientLoan1);

			client1.addClientLoan(clientLoan2);
			loan2.addClientLoan(clientLoan2);
			clientLoan.save(clientLoan2);

			client2.addClientLoan(clientLoan3);
			loan2.addClientLoan(clientLoan3);
			clientLoan.save(clientLoan3);

			client2.addClientLoan(clientLoan4);
			loan3.addClientLoan(clientLoan4);
			clientLoan.save(clientLoan4);
		};
	}
}
