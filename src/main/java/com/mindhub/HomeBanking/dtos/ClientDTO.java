package com.mindhub.HomeBanking.dtos;
import com.mindhub.HomeBanking.models.Client;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class ClientDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<AccountDTO> accounts;
    private List<ClientLoanDTO> loans;
    private List<CardDTO> cards;

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(toList());
        this.loans = client.getClientLoans().stream().map(loan -> new ClientLoanDTO(loan)).collect(toList());
        this.cards = client.getCards().stream().map(card -> new CardDTO(card)).collect(toList());
    }
    public long getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public List<AccountDTO> getAccounts() {return accounts;}
    public List<ClientLoanDTO> getLoans() {return loans;}
    public List<CardDTO> getCards() {return cards;}
}
