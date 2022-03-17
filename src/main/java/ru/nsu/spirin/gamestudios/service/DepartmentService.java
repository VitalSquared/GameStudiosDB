package ru.nsu.spirin.gamestudios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.spirin.gamestudios.model.entity.Department;
import ru.nsu.spirin.gamestudios.repository.DepartmentRepository;
import ru.nsu.spirin.gamestudios.repository.EmployeeRepository;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public Department getDepartmentByID(Long departmentID) {
        return this.departmentRepository.findByID(departmentID);
    }

    public List<Department> getAllDepartmentsOfStudio(Long studioID, String sortField, String sortDir) {
        return this.departmentRepository.findAllByStudioIDSorted(studioID, sortField, sortDir);
    }

    public List<Department> getAllDepartmentsSorted(String sortField, String sortDir) {
        return this.departmentRepository.findAllSorted(sortField, sortDir);
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

    public void deleteDepartment(Long departmentID) {
        Department department = this.departmentRepository.findByID(departmentID);
        Department rootDepartment = this.departmentRepository.findRootDepartmentByStudioID(department.getStudioID());
        this.employeeRepository.updateDevelopersDepartment(departmentID, rootDepartment.getDepartmentID());
        this.departmentRepository.delete(departmentID);
    }
}
