package ru.nsu.spirin.gamestudios.model.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
public class Employee {
    private Long employeeID;

    @NotBlank
    private String firstName;

    @NotBlank
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Employee)) return false;
        Employee that = (Employee) object;
        return this.employeeID.equals(that.employeeID);
    }
}
