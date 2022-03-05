package ru.nsu.spirin.gamestudios.model.entity.account;

import lombok.Data;

@Data
public class Account {
    private String login;
    private String passwordHash;
    private Long employeeID;
    private Boolean active;

    private String passwordResetState;

    public Account(String login, String passwordHash, Long employeeID, Boolean active) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.employeeID = employeeID;
        this.active = active;
    }
}
