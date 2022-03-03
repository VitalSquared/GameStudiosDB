package ru.nsu.spirin.gamestudios.model;

import lombok.Data;

import java.sql.Date;

@Data
public class Employee {
    private Long employeeID;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Long categoryID;
    private Long studioID;
    private Long departmentID;
    private Boolean active;

    private String employeeType;

    public Employee(Long employeeID,
                    String firstName,
                    String lastName,
                    Date birthDate,
                    Long categoryID,
                    Long studioID,
                    Long departmentID,
                    Boolean active) {
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
