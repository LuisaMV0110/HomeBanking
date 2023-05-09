package com.mindhub.HomeBanking.services.implement;

import com.mindhub.HomeBanking.dtos.AccountDTO;
import com.mindhub.HomeBanking.models.Account;
import com.mindhub.HomeBanking.repositories.AccountRepository;
import com.mindhub.HomeBanking.services.AccountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImplement implements AccountServices {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }
    @Override
    public Optional<AccountDTO> getAccountId(Long id) {
        Optional<Account> optionalAccount = this.findById(id);
        return optionalAccount.map(account -> new AccountDTO(account));
    }
    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }
    @Override
    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }
}
