package ru.nsu.spirin.gamestudios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.spirin.gamestudios.model.entity.Department;
import ru.nsu.spirin.gamestudios.repository.DepartmentRepository;

import java.sql.SQLException;
import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department getDepartmentByID(Long departmentID) {
        return this.departmentRepository.findByID(departmentID);
    }

    public List<Department> getAllDepartmentsOfStudio(Long studioID) {
        return this.departmentRepository.findAllByStudioID(studioID);
    }

    public List<Department> getAllDepartments() {
        return this.departmentRepository.findAll();
    }

    public void newDepartment(Department department) {
        if (department.getName() == null) {
            return;
        }
        department.setHeadID(department.getHeadID() == -1 ? null : department.getHeadID());
        this.departmentRepository.save(department);
    }

    public void updateDepartment(Long departmentID, Department department) {
        if (department.getName() == null) {
            return;
        }
        department.setHeadID(department.getHeadID() == -1 ? null : department.getHeadID());
        this.departmentRepository.update(departmentID, department);
    }
}
