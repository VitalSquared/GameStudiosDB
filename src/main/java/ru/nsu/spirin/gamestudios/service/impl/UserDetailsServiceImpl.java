package ru.nsu.spirin.gamestudios.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nsu.spirin.gamestudios.dao.RoleDAO;
import ru.nsu.spirin.gamestudios.dao.UserDAO;
import ru.nsu.spirin.gamestudios.model.user.Role;
import ru.nsu.spirin.gamestudios.model.user.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDAO userDAO;
    private final RoleDAO roleDAO;

    @Autowired
    public UserDetailsServiceImpl(UserDAO userDAO, RoleDAO roleDAO) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userDAO.findUserAccount(email);

        if (user == null) {
            System.err.println("User not found! " + email);
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }

        List<Role> roles = roleDAO.getEmployeeRoles(user.getEmployeeID());
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (var role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(), user.getPasswordHash(),
                user.isActive(), user.isActive(), user.isActive(), user.isActive(),
                authorities
        );
    }
}
