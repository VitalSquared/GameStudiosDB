package ru.nsu.spirin.gamestudios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nsu.spirin.gamestudios.dao.RoleDAO;
import ru.nsu.spirin.gamestudios.dao.AccountDAO;
import ru.nsu.spirin.gamestudios.model.entity.account.Account;
import ru.nsu.spirin.gamestudios.model.entity.account.Role;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountDAO accountDAO;
    private final RoleDAO roleDAO;

    @Autowired
    public UserDetailsServiceImpl(AccountDAO accountDAO, RoleDAO roleDAO) {
        this.accountDAO = accountDAO;
        this.roleDAO = roleDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = this.accountDAO.findUserAccount(email);

        if (account == null) {
            System.err.println("User not found! " + email);
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }

        List<Role> roles = roleDAO.getEmployeeRoles(account.getEmployeeID());
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (var role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        }

        return new org.springframework.security.core.userdetails.User(
                account.getLogin(), account.getPasswordHash(),
                account.getActive(), account.getActive(), account.getActive(), account.getActive(),
                authorities
        );
    }
}
