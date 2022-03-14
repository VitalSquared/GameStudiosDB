package ru.nsu.spirin.gamestudios.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PasswordChangeStore {
    @NotEmpty
    private String oldPassword;

    @NotEmpty
    private String newPassword;

    @NotEmpty
    private String newPasswordRepeat;
}
