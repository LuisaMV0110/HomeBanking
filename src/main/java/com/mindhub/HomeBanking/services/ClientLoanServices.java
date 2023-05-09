package com.mindhub.HomeBanking.services;

import com.mindhub.HomeBanking.models.Client;
import com.mindhub.HomeBanking.models.ClientLoan;
import com.mindhub.HomeBanking.models.Loan;

public interface ClientLoanServices {
    void saveClientLoan(ClientLoan clientLoan);
    ClientLoan findByLoanAndClient(Loan loan, Client client);
}
