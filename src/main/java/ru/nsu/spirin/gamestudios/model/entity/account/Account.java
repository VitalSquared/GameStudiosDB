package ru.nsu.spirin.gamestudios.model.entity.account;

import lombok.Data;

@Data
public class Account {
    private String email;
    private String passwordHash;
    private Long employeeID;
    private Boolean active;

    private String passwordResetState;

    public Account(String email, String passwordHash, Long employeeID, Boolean active) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.employeeID = employeeID;
        this.active = active;
    }
}
