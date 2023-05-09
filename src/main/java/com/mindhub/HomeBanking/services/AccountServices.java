package com.mindhub.HomeBanking.services;

import com.mindhub.HomeBanking.dtos.AccountDTO;
import com.mindhub.HomeBanking.models.Account;

import java.util.Optional;

public interface AccountServices {
void saveAccount(Account account);
Optional<AccountDTO> getAccountId(Long id);
Optional<Account> findById(Long id);
Account findByNumber(String number);
}
