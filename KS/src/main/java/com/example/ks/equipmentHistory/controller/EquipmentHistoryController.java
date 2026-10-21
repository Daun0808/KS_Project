package com.example.ks.equipmentHistory.controller;

import com.example.ks.equipment.domain.Equipment;
import com.example.ks.equipment.service.EquipmentService;
import com.example.ks.equipmentHistory.domain.EquipmentHistory;
import com.example.ks.equipmentHistory.dto.CreateEquipmentHistory;
import com.example.ks.equipmentHistory.dto.UpdateEquipmentHistory;
import com.example.ks.equipmentHistory.service.EquipmentHistoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping
public class EquipmentHistoryController {
    private final EquipmentHistoryService equipmentHistoryService;
    private final EquipmentService equipmentService;

    public EquipmentHistoryController(EquipmentHistoryService equipmentHistoryService, EquipmentService equipmentService) {
        this.equipmentHistoryService = equipmentHistoryService;
        this.equipmentService = equipmentService;
    }

    @GetMapping("/equipment/{equipment_id}/history")
    public ModelAndView findAllByEquipment_EquipmentId(@PathVariable("equipment_id") int equipmentId) {
        ModelAndView modelAndView = new ModelAndView("equipmentHistory");

        Equipment equipment = equipmentService.findByEquipmentId(equipmentId);
        List<EquipmentHistory> repairHistory = equipmentHistoryService.findByRepair_EquipmentId(equipmentId);
        List<EquipmentHistory> changeHistory = equipmentHistoryService.findByLocate_EquipmentId(equipmentId);

        modelAndView.addObject("equipment", equipment);
        modelAndView.addObject("repairHistory", repairHistory);
        modelAndView.addObject("changeHistory", changeHistory);

        return modelAndView;
    }

    @GetMapping("/equipment/{equipment_id}/repair")
    public ModelAndView createRepairForm(@PathVariable("equipment_id") Integer equipmentId) {
        ModelAndView modelAndView = new ModelAndView("equipmentRepairCreate");
        modelAndView.addObject("equipment", equipmentService.findByEquipmentId(equipmentId));
        return modelAndView;
    }

    @PostMapping("/equipment/repair")
    public String createRepair(@Valid @ModelAttribute("repairHistory") CreateEquipmentHistory dto) {
        equipmentHistoryService.createEquipmentHistory(dto);
        return "redirect:/equipment/" + dto.equipmentId() + "/history";
    }

    @GetMapping("/equipment/history/edit/{historyId}")
    public ModelAndView editEquipmentHistoryForm(@PathVariable("historyId") int historyId) {
        EquipmentHistory history = equipmentHistoryService.getEquipmentHistory(historyId);
        ModelAndView modelAndView = new ModelAndView("equipmentHistoryEdit");
        modelAndView.addObject("history", history);
        return modelAndView;
    }

    @PostMapping("/equipment/history/update")
    public String updateEquipmentHistory(@Valid @ModelAttribute("history") UpdateEquipmentHistory dto) {
        equipmentHistoryService.updateEquipmentHistory(dto);
        return "redirect:/equipment/" + dto.equipmentId() + "/history";
    }

    @GetMapping("/equipment/history/delete/{historyId}")
    public String deleteEquipmentHistory(@PathVariable("historyId") int historyId) {
        EquipmentHistory equipmentHistory = equipmentHistoryService.deleteEquipmentHistory(historyId);
        return "redirect:/equipment/" + equipmentHistory.getEquipment().getEquipmentId() + "/history";
    }
}
