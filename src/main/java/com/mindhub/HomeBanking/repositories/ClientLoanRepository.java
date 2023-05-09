package com.mindhub.HomeBanking.repositories;

import com.mindhub.HomeBanking.models.Client;
import com.mindhub.HomeBanking.models.ClientLoan;
import com.mindhub.HomeBanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClientLoanRepository extends JpaRepository <ClientLoan, Long>{
    ClientLoan findByLoanAndClient (Loan loan, Client client);
}
