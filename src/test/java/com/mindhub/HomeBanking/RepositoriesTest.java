package com.mindhub.HomeBanking;
import com.mindhub.HomeBanking.models.*;
import com.mindhub.HomeBanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;

    @DataJpaTest
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
    public class RepositoriesTest {


//        Client Repository
        @Autowired
        ClientRepository clientRepository;

        @Test
        public void existClientEmail(){
            List<Client> clients = clientRepository.findAll();
            assertThat(clients, hasItem(hasProperty("email",is(not(empty())))));
        }

        @Test
        public void existClientFirstName(){
            List<Client> clients = clientRepository.findAll();
            assertThat(clients, hasItem((hasProperty("firstName", isA(String.class)))));
        }

//        Account Repository
        @Autowired
        AccountRepository accountRepository;

        @Test
        public void existNumberAccount(){
            List<Account> accounts = accountRepository.findAll();
            assertThat(accounts, hasItem(hasProperty("number", is("VIN-002"))));
        }

        @Test
        public void existActiveAccount(){
            List<Account> accounts = accountRepository.findAll();
            assertThat(accounts, hasItem(hasProperty("accountActive", is(true))));
        }
//        Card Repository
        @Autowired
        CardRepository cardRepository;

        @Test
        public void existCards(){
            List<Card> cards = cardRepository.findAll();
            assertThat(cards, is(not(empty())));
        }
        @Test
        public void existActiveCard(){
            List<Card> cards = cardRepository.findAll();
            assertThat(cards, hasItem(hasProperty("cardActivate", is(true))));
        }
//        Transaction Repository
        @Autowired
        TransactionRepository transactionRepository;

        @Test
        public void existActiveDebitTransaction(){
            List<Transaction> debitTransaction = transactionRepository.findAll().stream().filter(transaction -> transaction.getType() == TransactionType.DEBIT).collect(toList());
            assertThat(debitTransaction, hasItem((hasProperty("transactionActive", is(true)))));
        }
        @Test
        public void existActiveCreditTransaction(){
            List<Transaction> creditTransaction = transactionRepository.findAll().stream().filter(transaction -> transaction.getType() == TransactionType.CREDIT).collect(toList());
            assertThat(creditTransaction, hasItem((hasProperty("transactionActive", is(true)))));
        }

//        Loan Repository
        @Autowired
        LoanRepository loanRepository;

        @Test
        public void existLoans(){
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans,is(not(empty())));
        }
        @Test
        public void existPersonalLoan(){
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans, hasItem(hasProperty("name", is("Automotive"))));
        }

    }

