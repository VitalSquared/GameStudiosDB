package ru.nsu.spirin.gamestudios.model.entity;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
public class Employee {
    private Long employeeID;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotNull
    private Date birthDate;

    private Long categoryID;

    private Long studioID;

    private Long departmentID;

    private Boolean active;


    private String employeeType;

    public Employee(Long employeeID, String firstName, String lastName, Date birthDate,
                    Long categoryID, Long studioID, Long departmentID, Boolean active) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.categoryID = categoryID;
        this.studioID = studioID;
        this.departmentID = departmentID;
        this.active = active;
    }
}
