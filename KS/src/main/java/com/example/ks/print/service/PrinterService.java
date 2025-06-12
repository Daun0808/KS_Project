package com.example.ks.print.service;


import com.example.ks.department.domain.Department;
import com.example.ks.department.repository.DepartmentRepository;
import com.example.ks.print.domain.Printer;
import com.example.ks.print.dto.CreatePrinter;
import com.example.ks.print.dto.UpdatePrinter;
import com.example.ks.print.repository.PrinterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class PrinterService {
    private final PrinterRepository printerRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public List<Printer> findAll() {
        return printerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Printer findByPrinterId(int printerId) {
        return printerRepository.findByPrinterId(printerId);
    }

    @Transactional(readOnly = true)
    public List<Printer> findByDepartment_DepartmentName(String departmentName) {
        return  printerRepository.findByDepartment_DepartmentName(departmentName);
    }

    @Transactional(readOnly = true)
    public List<Printer> findByDepartment_DepartmentFloor(String departmentFloor) {
        return  printerRepository.findByDepartment_DepartmentFloor(departmentFloor);
    }

    public Printer create(CreatePrinter createPrinter) {
        Department department = departmentRepository.findById(Integer.valueOf(createPrinter.departmentId())).orElse(null);
        return printerRepository.save(Printer.toEntity(createPrinter, department));
    }

    public void update(UpdatePrinter updatePrinter) {
        Department department = departmentRepository.findById(Integer.valueOf(updatePrinter.departmentId())).orElse(null);

        Printer printer = printerRepository.findById(updatePrinter.printerId())
                .orElseThrow(() -> new RuntimeException("프린터를 찾을 수 없습니다."));
        printer.Update(updatePrinter, department);
        printerRepository.save(printer);
    }

}
