package com.example.ks.equipmentHistory.service;

import com.example.ks.equipment.domain.Equipment;
import com.example.ks.equipment.repository.EquipmentRepository;
import com.example.ks.equipmentHistory.domain.EquipmentHistory;
import com.example.ks.equipmentHistory.dto.CreateEquipmentHistory;
import com.example.ks.equipmentHistory.dto.UpdateEquipmentHistory;
import com.example.ks.equipmentHistory.repository.EquipmentHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EquipmentHistoryService {
    private final EquipmentHistoryRepository equipmentHistoryRepository;
    private final EquipmentRepository equipmentRepository;

    public EquipmentHistoryService(EquipmentHistoryRepository equipmentHistoryRepository, EquipmentRepository equipmentRepository) {
        this.equipmentHistoryRepository = equipmentHistoryRepository;
        this.equipmentRepository = equipmentRepository;
    }

    @Transactional(readOnly = true)
    public List<EquipmentHistory> findByEquipment_EquipmentId(int equipmentId) {
        return equipmentHistoryRepository.findAllByEquipment_EquipmentId(equipmentId);
    }

    @Transactional(readOnly = true)
    public List<EquipmentHistory> findByRepair_EquipmentId(int equipmentId) {
        List<EquipmentHistory> historyList = equipmentHistoryRepository.findAllByEquipment_EquipmentIdOrderByEquipmentRepairDateDesc(equipmentId);

        return historyList.stream()
                .filter(history -> "수리".equals(history.getHistoryTag()))
                .filter(history -> "N".equals(history.getDel()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EquipmentHistory> findByLocate_EquipmentId(int equipmentId) {
        List<EquipmentHistory> historyList = equipmentHistoryRepository.findAllByEquipment_EquipmentIdOrderByEquipmentRepairDateDesc(equipmentId);

        return historyList.stream()
                .filter(history -> "부서 변경".equals(history.getHistoryTag()))
                .filter(history -> "N".equals(history.getDel()))
                .toList();
    }

    public void createEquipmentHistory(CreateEquipmentHistory createEquipmentHistory) {
        Equipment equipment = equipmentRepository.findByEquipmentId(createEquipmentHistory.equipmentId());
        equipmentHistoryRepository.save(EquipmentHistory.toEntity(createEquipmentHistory, equipment));
    }

    public void updateEquipmentHistory(UpdateEquipmentHistory updateEquipmentHistory) {
        EquipmentHistory equipmentHistory = equipmentHistoryRepository.findById(updateEquipmentHistory.equipmentHistoryId())
                .orElseThrow(() -> new RuntimeException("장비 히스토리를 찾을 수 없습니다."));
        equipmentHistory.update(updateEquipmentHistory);
        equipmentHistoryRepository.save(equipmentHistory);
    }

    public EquipmentHistory deleteEquipmentHistory(int equipmentHistoryId) {
        EquipmentHistory equipmentHistory = equipmentHistoryRepository.findById(equipmentHistoryId)
                .orElseThrow(() -> new RuntimeException("장비 히스토리를 찾을 수 없습니다."));
        equipmentHistory.delete("Y");
        return equipmentHistoryRepository.save(equipmentHistory);
    }

    @Transactional(readOnly = true)
    public EquipmentHistory getEquipmentHistory(int equipmentHistoryId) {
        return equipmentHistoryRepository.findByEquipmentHistoryId(equipmentHistoryId);
    }
}
