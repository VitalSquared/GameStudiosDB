package ru.nsu.spirin.gamestudios.model.account;

import lombok.Data;

@Data
public class Account {
    private String login;
    private String passwordHash;
    private Long employeeID;
    private boolean active;

    private String passwordResetState;

    public Account() {

    }

    public Account(String login, String passwordHash, Long employeeID, boolean active) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.employeeID = employeeID;
        this.active = active;
    }
}
