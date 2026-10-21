package com.example.ks.equipment.controller;

import com.example.ks.department.domain.Department;
import com.example.ks.department.service.DepartmentService;
import com.example.ks.equipment.domain.Equipment;
import com.example.ks.equipment.dto.CreateEquipment;
import com.example.ks.equipment.dto.UpdateEquipment;
import com.example.ks.equipment.service.EquipmentService;
import com.example.ks.equipmentHistory.dto.CreateEquipmentHistory;
import com.example.ks.equipmentHistory.service.EquipmentHistoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping
public class EquipmentController {
    private final DepartmentService departmentService;
    private final EquipmentService equipmentService;
    private final EquipmentHistoryService equipmentHistoryService;

    public EquipmentController(DepartmentService departmentService, EquipmentService equipmentService, EquipmentHistoryService equipmentHistoryService) {
        this.departmentService = departmentService;
        this.equipmentService = equipmentService;
        this.equipmentHistoryService = equipmentHistoryService;
    }

    @GetMapping("/equipment")
    public ModelAndView equipment() {
        ModelAndView modelAndView = new ModelAndView("equipment");
        List<Equipment> equipments = equipmentService.findAll()
                .stream()
                .filter(equipment -> !"Y".equals(equipment.getDel()))
                .sorted(Comparator
                        .comparing((Equipment e) -> e.getDepartment().getDepartmentFloor(), Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(e -> e.getDepartment().getDepartmentName(), Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(Equipment::getEquipmentPlace, Comparator.nullsLast(Comparator.naturalOrder()))
                )
                .toList();
        modelAndView.addObject("equipments", equipments);
        return modelAndView;
    }

    @GetMapping("/equipment/create")
    public ModelAndView createEquipmentForm() {
        ModelAndView modelAndView = new ModelAndView("equipmentCreate");
        List<Department> departments = departmentService.findAll()
                .stream()
                .filter(dept -> "N".equals(dept.getDelete()))
                .toList();

        modelAndView.addObject("equipment", CreateEquipment.builder().build());
        modelAndView.addObject("departments", departments);
        return modelAndView;
    }

    @PostMapping("/equipment/create")
    public String createEquipment(@Valid @ModelAttribute CreateEquipment createEquipment) {
        equipmentService.create(createEquipment);
        return "redirect:/equipment";
    }

    @GetMapping("/equipment/{equipment_id}")
    public ModelAndView findEquipmentById(@PathVariable("equipment_id") int equipmentId) {
        ModelAndView modelAndView = new ModelAndView("equipmentDetail");
        Equipment equipment = equipmentService.findByEquipmentId(equipmentId);
        modelAndView.addObject("equipment", equipment);
        modelAndView.addObject("equipment_id", equipmentId);
        return modelAndView;
    }

    @GetMapping("/equipment/update/{equipment_id}")
    public ModelAndView updateEquipmentForm(@PathVariable("equipment_id") int equipmentId) {
        ModelAndView modelAndView = new ModelAndView("equipmentUpdate");
        Equipment equipment = equipmentService.findByEquipmentId(equipmentId);
        List<Department> departments = departmentService.findAll()
                .stream()
                .filter(dept -> "N".equals(dept.getDelete()))
                .toList();

        modelAndView.addObject("equipment", equipment);
        modelAndView.addObject("departments", departments);
        return modelAndView;
    }

    @PostMapping("/equipment/update")
    public String updateEquipment(@Valid @ModelAttribute UpdateEquipment updateEquipment) {
        Equipment existingEquipment = equipmentService.findByEquipmentId(updateEquipment.equipmentId());
        String currentDepartmentId = String.valueOf(existingEquipment.getDepartment().getDepartmentId());

        // 부서가 변경된 경우에만 히스토리 기록 (프린터와 동일한 규칙)
        if (!currentDepartmentId.equals(updateEquipment.departmentId())) {
            Department beforeDept = existingEquipment.getDepartment();
            Department afterDept = departmentService.findById(Integer.parseInt(updateEquipment.departmentId()));

            CreateEquipmentHistory historyDto = CreateEquipmentHistory.builder()
                    .departmentBeforeName(beforeDept.getDepartmentName())
                    .departmentNewName(afterDept.getDepartmentName())
                    .equipmentAfterDate(LocalDate.now())
                    .equipmentText("부서 변경에 따른 이력 자동 생성")
                    .equipmentRepair(null)
                    .equipmentRepairDate(null)
                    .historyTag("부서 변경")
                    .del("N")
                    .equipmentId(updateEquipment.equipmentId())
                    .build();

            equipmentHistoryService.createEquipmentHistory(historyDto);
        }
        equipmentService.update(updateEquipment);
        return "redirect:/equipment/" + updateEquipment.equipmentId();
    }
}
