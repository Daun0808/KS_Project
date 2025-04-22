package com.example.ks.print.repository;

import com.example.ks.department.domain.Department;
import com.example.ks.print.domain.Printer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PrinterRepository extends JpaRepository<Printer, Integer> {
    Printer findByPrinterId(int printerId);
    List<Printer> findByDepartment_DepartmentName(String departmentName);
    List<Printer> findByDepartment_DepartmentFloor(String departmentFloor);


}
