package ru.nsu.spirin.gamestudios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nsu.spirin.gamestudios.model.entity.account.Account;
import ru.nsu.spirin.gamestudios.repository.AccountRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account findAccountByEmail(String email) {
        return this.accountRepository.findByID(email);
    }

    public List<Account> findAllAccounts() {
        return this.accountRepository.findAll();
    }

    public Account findAccountByEmployeeID(Long employeeID) {
        return this.accountRepository.findByEmployeeID(employeeID);
    }

    public Map<Long, String> getEmailsWithEmployeeIDs() {
        Map<Long, String> map = new HashMap<>();
        List<Account> accounts = findAllAccounts();
        for (var account : accounts) {
            map.put(account.getEmployeeID(), account.getEmail());
        }
        return map;
    }

    public void update(String email, String password) {
        Account account = new Account(email, new BCryptPasswordEncoder(12).encode(password), null, null);
        this.accountRepository.update(email, account);
    }
}
