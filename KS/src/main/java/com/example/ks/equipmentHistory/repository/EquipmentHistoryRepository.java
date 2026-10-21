package com.example.ks.equipmentHistory.repository;

import com.example.ks.equipmentHistory.domain.EquipmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentHistoryRepository extends JpaRepository<EquipmentHistory, Integer> {
    List<EquipmentHistory> findAllByEquipment_EquipmentId(int equipmentId);
    List<EquipmentHistory> findAllByEquipment_EquipmentIdOrderByEquipmentRepairDateDesc(int equipmentId);
    EquipmentHistory findByEquipmentHistoryId(int equipmentHistoryId);
}
