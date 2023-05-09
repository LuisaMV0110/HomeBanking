package com.mindhub.HomeBanking.services.implement;

import com.mindhub.HomeBanking.models.Client;
import com.mindhub.HomeBanking.models.ClientLoan;
import com.mindhub.HomeBanking.models.Loan;
import com.mindhub.HomeBanking.repositories.ClientLoanRepository;
import com.mindhub.HomeBanking.services.ClientLoanServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientLoanServicesImplement implements ClientLoanServices {
    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @Override
    public void saveClientLoan(ClientLoan clientLoan) {
        clientLoanRepository.save(clientLoan);
    }
    @Override
    public ClientLoan findByLoanAndClient(Loan loan, Client client) {
        return clientLoanRepository.findByLoanAndClient(loan, client);
    }
}
