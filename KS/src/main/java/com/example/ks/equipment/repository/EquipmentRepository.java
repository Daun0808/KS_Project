package com.example.ks.equipment.repository;

import com.example.ks.equipment.domain.Equipment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
    Equipment findByEquipmentId(int equipmentId);
    List<Equipment> findByDepartment_DepartmentName(String departmentName);
    List<Equipment> findByDepartment_DepartmentFloor(String departmentFloor);

    @EntityGraph(attributePaths = "department")
    List<Equipment> findAll();
}
