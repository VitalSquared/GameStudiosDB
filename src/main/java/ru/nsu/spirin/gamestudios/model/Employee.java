package ru.nsu.spirin.gamestudios.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class Employee {
    private Long employeeID;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Long categoryID;
    private Long studioID;
    private Long departmentID;
    private boolean isDepartmentHead;
}
