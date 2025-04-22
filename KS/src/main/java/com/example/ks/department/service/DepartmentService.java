package com.example.ks.department.service;


import com.example.ks.department.domain.Department;
import com.example.ks.department.dto.request.CreateDepartment;
import com.example.ks.department.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Department findById(int id) {
        Department department =  departmentRepository.findById(id).orElse(null);
//        if (department == null) {
//            ErrorStatus errorStatus = ErrorStatus.from("부서를 찾을 수 없습니다", HttpStatus.NOT_FOUND,
//                    LocalDateTime.now());
//            throw new FailException(errorStatus);
//        }
        return department;
    }

    @Transactional(readOnly = true)
    public Department findByName(String name) {
        Department department = departmentRepository.findByDepartmentName(name);
//        if (department == null) {
//            ErrorStatus errorStatus = ErrorStatus.from("부서를 찾을 수 없습니다", HttpStatus.NOT_FOUND,
//                    LocalDateTime.now());
//            throw new FailException(errorStatus);
//        }
        return department;
    }

    @Transactional(readOnly = true)
    public Department findByFloor(String floor) {
        Department department = departmentRepository.findByDepartmentFloor(floor);
//        if (department == null) {
//            ErrorStatus errorStatus = ErrorStatus.from("부서를 찾을 수 없습니다", HttpStatus.NOT_FOUND,
//                    LocalDateTime.now());
//            throw new FailException(errorStatus);
//        }
        return department;
    }

    public Department create(CreateDepartment createDepartment) {
        return departmentRepository.save(Department.toEntity(createDepartment));
    }

    public Department update(CreateDepartment updateDepartment, int id) {
        Department department = departmentRepository.findById(id).orElse(null);
//        if (department == null) {
//            ErrorStatus errorStatus = ErrorStatus.from("부서를 찾을 수 없습니다", HttpStatus.NOT_FOUND,
//                    LocalDateTime.now());
//            throw new FailException(errorStatus);
//        }
        Objects.requireNonNull(department).update(updateDepartment);
        return departmentRepository.save(department);
    }

}
