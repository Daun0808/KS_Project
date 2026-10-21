package com.example.ks.equipmentHistory.domain;

import com.example.ks.equipment.domain.Equipment;
import com.example.ks.equipmentHistory.dto.CreateEquipmentHistory;
import com.example.ks.equipmentHistory.dto.UpdateEquipmentHistory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "equipment_history")
@NoArgsConstructor
@Getter
public class EquipmentHistory {

    @Id
    @Column(name = "equipment_history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int equipmentHistoryId;

    @Column(name = "department_before_name")
    private String departmentBeforeName;

    @Column(name = "department_new_name")
    private String departmentNewName;

    @Column(name = "equipment_after_date")
    private LocalDate equipmentAfterDate;

    @Column(name = "equipment_text")
    private String equipmentText;

    @Column(name = "equipment_repair")
    private String equipmentRepair;

    @Column(name = "equipment_repair_date")
    private LocalDate equipmentRepairDate;

    @Column(name = "history_tag")
    private String historyTag;

    @Column(name = "del")
    private String del;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @Builder
    public EquipmentHistory(
            Equipment equipment,
            String departmentBeforeName,
            String departmentNewName,
            LocalDate equipmentAfterDate,
            String equipmentText,
            String equipmentRepair,
            LocalDate equipmentRepairDate,
            String historyTag,
            String del
    ) {
        this.equipment = equipment;
        this.departmentBeforeName = departmentBeforeName;
        this.departmentNewName = departmentNewName;
        this.equipmentAfterDate = equipmentAfterDate;
        this.equipmentText = equipmentText;
        this.equipmentRepair = equipmentRepair;
        this.equipmentRepairDate = equipmentRepairDate;
        this.historyTag = historyTag;
        this.del = del;
    }

    public static EquipmentHistory toEntity(CreateEquipmentHistory dto, Equipment equipment) {
        return EquipmentHistory.builder()
                .equipment(equipment)
                .departmentBeforeName(dto.departmentBeforeName())
                .departmentNewName(dto.departmentNewName())
                .equipmentAfterDate(dto.equipmentAfterDate())
                .equipmentText(dto.equipmentText())
                .equipmentRepair(dto.equipmentRepair())
                .equipmentRepairDate(dto.equipmentRepairDate())
                .historyTag(dto.historyTag())
                .del(dto.del())
                .build();
    }

    public void update(UpdateEquipmentHistory dto) {
        this.departmentBeforeName = dto.departmentBeforeName();
        this.departmentNewName = dto.departmentNewName();
        this.equipmentAfterDate = dto.equipmentAfterDate();
        this.equipmentText = dto.equipmentText();
        this.equipmentRepair = dto.equipmentRepair();
        this.equipmentRepairDate = dto.equipmentRepairDate();
        this.del = dto.del();
    }

    public void delete(String del) {
        this.del = del;
    }
}
