package com.example.ks.department.repository;

import com.example.ks.department.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Department findByDepartmentId(int departmentId);
    Department findByDepartmentName(String name);
    Department findByDepartmentFloor(String floor);
    Department findByDelete(String delete);
    Department findByDepartmentIdAndDelete(int departmentId, String delete);
}
