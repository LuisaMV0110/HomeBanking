package com.mindhub.HomeBanking;

import com.mindhub.HomeBanking.models.Account;
import com.mindhub.HomeBanking.models.Client;
import com.mindhub.HomeBanking.models.Transaction;
import com.mindhub.HomeBanking.models.TransactionType;
import com.mindhub.HomeBanking.repositories.AccountRepository;
import com.mindhub.HomeBanking.repositories.ClientRepository;
import com.mindhub.HomeBanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class HomeBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeBankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository client, AccountRepository account, TransactionRepository transaction){
		return(args) -> {
			Client client1 = new Client("Melba","Morel","melba@mindhub.com");
			client.save(client1);

			Account account1 = new Account("VIN001", LocalDateTime.now(),5000);
			Account account2 = new Account("VIN002", LocalDateTime.now().plusDays(1),7500);

			client1.addAccount(account1);
			client1.addAccount(account2);

			Transaction transaction1 = new Transaction(TransactionType.CREDIT,3000,"Amazon",LocalDateTime.now());
			Transaction transaction2 = new Transaction(TransactionType.DEBIT,2000,"Amazon",LocalDateTime.now());

			account.save(account1);
			account.save(account2);

			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);

			transaction.save(transaction1);
			transaction.save(transaction2);

			Transaction transaction3 = new Transaction(TransactionType.CREDIT,3000,"Amazon",LocalDateTime.now());
			Transaction transaction4 = new Transaction(TransactionType.DEBIT,2000,"Mercado Pago",LocalDateTime.now());

			account2.addTransaction(transaction3);
			account2.addTransaction(transaction4);

			transaction.save(transaction3);
			transaction.save(transaction4);

			Client client2 = new Client("Nicoll","Laguna","Nicoll@gmail.com");
			client.save(client2);

			Account account3 = new Account("VIN003", LocalDateTime.now(),50000);
			Account account4 = new Account("VIN004", LocalDateTime.now().plusDays(4),100000);

			client2.addAccount(account3);
			client2.addAccount(account4);

			Transaction transaction5 = new Transaction(TransactionType.CREDIT,3000,"Amazon",LocalDateTime.now());
			Transaction transaction6 = new Transaction(TransactionType.DEBIT,2000,"Mercado Pago",LocalDateTime.now());

			account.save(account3);
			account.save(account4);

			account3.addTransaction(transaction5);
			account3.addTransaction(transaction6);

			transaction.save(transaction5);
			transaction.save(transaction6);

			Transaction transaction7 = new Transaction(TransactionType.CREDIT,78000,"Amazon",LocalDateTime.now());
			Transaction transaction8 = new Transaction(TransactionType.DEBIT,12000,"Mercado Pago",LocalDateTime.now());

			account4.addTransaction(transaction7);
			account4.addTransaction(transaction8);

			transaction.save(transaction7);
			transaction.save(transaction8);
		};
	}
}
