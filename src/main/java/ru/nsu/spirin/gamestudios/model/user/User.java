package ru.nsu.spirin.gamestudios.model.user;

import lombok.Data;

@Data
public class User {
    private String login;
    private String passwordHash;
    private Long employeeID;
    private boolean active;

    public User() {

    }

    public User(String login, String passwordHash, Long employeeID, boolean active) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.employeeID = employeeID;
        this.active = active;
    }
}
