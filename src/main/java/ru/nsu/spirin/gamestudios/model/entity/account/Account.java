package ru.nsu.spirin.gamestudios.model.entity.account;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Account {
    @NotBlank
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

    @Override
    public boolean equals (Object object) {
        if (object == this) return true;
        if (!(object instanceof Account)) return false;
        Account that = (Account) object;
        return this.email.equals(that.email);
    }
}
