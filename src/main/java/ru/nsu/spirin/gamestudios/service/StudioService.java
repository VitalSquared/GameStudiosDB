package ru.nsu.spirin.gamestudios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.spirin.gamestudios.model.entity.Department;
import ru.nsu.spirin.gamestudios.model.entity.Studio;
import ru.nsu.spirin.gamestudios.repository.*;

import java.util.List;

@Service
public class StudioService {
    private final StudioRepository studioRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final GameRepository gameRepository;
    private final TestAppRepository testAppRepository;

    @Autowired
    public StudioService(StudioRepository studioRepository, EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, GameRepository gameRepository, TestAppRepository testAppRepository) {
        this.studioRepository = studioRepository;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.gameRepository = gameRepository;
        this.testAppRepository = testAppRepository;
    }

    public Studio getStudioByID(Long studioID) {
        return this.studioRepository.findByID(studioID);
    }

    public List<Studio> getStudiosListByID(Long studioID) {
        return this.studioRepository.findAllByID(studioID);
    }

    public List<Studio> getAllStudios() {
        return this.studioRepository.findAll();
    }

    public void newStudio(Studio studio) {
        if (studio.getName() == null || studio.getAddress() == null) {
            return;
        }
        this.studioRepository.save(studio);
    }

    public void updateStudio(Long id, Studio studio) {
        if (studio.getAddress() == null || studio.getName() == null) {
            return;
        }
        this.studioRepository.update(id, studio);
    }

    public boolean isStudioReferenced(Long studioID) {
        int size1 = this.employeeRepository.findAllDirectorsByStudioID(studioID).size();
        int size2 = this.gameRepository.findAllByStudioID(studioID).size();
        int size3 = this.testAppRepository.findAllByStudioID(studioID).size();
        if (size1 > 0 || size2 > 0 || size3 > 0) {
            return true;
        }
        List<Department> departmentList = this.departmentRepository.findAllByStudioID(studioID);
        return (departmentList.size() > 1) || (departmentList.size() != 0 && !departmentList.get(0).getIsRoot());
    }

    public void deleteStudio(Long studioID) {
        if (isStudioReferenced(studioID)) {
            return;
        }
        Department rootDepartment = this.departmentRepository.findRootDepartmentByStudioID(studioID);
        this.departmentRepository.delete(rootDepartment.getDepartmentID());
        this.studioRepository.delete(studioID);
    }
}
