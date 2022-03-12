package ru.nsu.spirin.gamestudios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nsu.spirin.gamestudios.model.entity.account.Account;
import ru.nsu.spirin.gamestudios.model.entity.account.Role;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountService accountService;
    private final EmployeeService employeeService;

    @Autowired
    public UserDetailsServiceImpl(AccountService accountService, EmployeeService employeeService) {
        this.accountService = accountService;
        this.employeeService = employeeService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = this.accountService.findAccountByEmail(email);

        if (account == null) {
            System.err.println("User not found! " + email);
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }

        List<Role> roles = employeeService.getEmployeeRoles(account.getEmployeeID());
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (var role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        }

        return new org.springframework.security.core.userdetails.User(
                account.getEmail(), account.getPasswordHash(),
                account.getActive(), account.getActive(), account.getActive(), account.getActive(),
                authorities
        );
    }
}
