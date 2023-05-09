package com.mindhub.HomeBanking.services.implement;

import com.mindhub.HomeBanking.dtos.LoanDTO;
import com.mindhub.HomeBanking.models.Loan;
import com.mindhub.HomeBanking.repositories.LoanRepository;
import com.mindhub.HomeBanking.services.LoanServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class LoanServicesImplement implements LoanServices {
@Autowired
private LoanRepository loanRepository;
    @Override
    public List<LoanDTO> getLoanDTO() {
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(toList());
    }
    @Override
    public Loan findById(long id) {
        return loanRepository.findById(id).orElse(null);
    }

}
