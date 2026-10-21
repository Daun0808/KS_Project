package com.example.ks.equipment.service;

import com.example.ks.department.domain.Department;
import com.example.ks.department.repository.DepartmentRepository;
import com.example.ks.equipment.domain.Equipment;
import com.example.ks.equipment.dto.CreateEquipment;
import com.example.ks.equipment.dto.UpdateEquipment;
import com.example.ks.equipment.repository.EquipmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;
    private final DepartmentRepository departmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository, DepartmentRepository departmentRepository) {
        this.equipmentRepository = equipmentRepository;
        this.departmentRepository = departmentRepository;
    }

    @Transactional(readOnly = true)
    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Equipment findByEquipmentId(int equipmentId) {
        return equipmentRepository.findByEquipmentId(equipmentId);
    }

    @Transactional(readOnly = true)
    public List<Equipment> findByDepartment_DepartmentName(String departmentName) {
        return equipmentRepository.findByDepartment_DepartmentName(departmentName);
    }

    @Transactional(readOnly = true)
    public List<Equipment> findByDepartment_DepartmentFloor(String departmentFloor) {
        return equipmentRepository.findByDepartment_DepartmentFloor(departmentFloor);
    }

    public Equipment create(CreateEquipment createEquipment) {
        Department department = departmentRepository.findById(Integer.valueOf(createEquipment.departmentId())).orElse(null);
        return equipmentRepository.save(Equipment.toEntity(createEquipment, department));
    }

    public void update(UpdateEquipment updateEquipment) {
        Department department = departmentRepository.findById(Integer.valueOf(updateEquipment.departmentId())).orElse(null);

        Equipment equipment = equipmentRepository.findById(updateEquipment.equipmentId())
                .orElseThrow(() -> new RuntimeException("장비를 찾을 수 없습니다."));
        equipment.update(updateEquipment, department);
        equipmentRepository.save(equipment);
    }
}
