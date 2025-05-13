package com.example.ks.computer.service;

import com.example.ks.computer.domain.Computer;
import com.example.ks.computer.dto.CreateComputer;
import com.example.ks.computer.dto.UpdateComputer;
import com.example.ks.computer.repository.ComputerRepository;
import com.example.ks.department.domain.Department;
import com.example.ks.department.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class ComputerService {

    private final ComputerRepository computerRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public List<Computer> findAll() {
        return computerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Computer findByComputerId(int computerId) {
        return computerRepository.findById(computerId)
                .orElseThrow(() -> new RuntimeException("컴퓨터를 찾을 수 없습니다."));
    }

    public Computer create(CreateComputer createComputer) {
        Department department = departmentRepository.findById(Integer.valueOf(createComputer.departmentId()))
                .orElseThrow(() -> new RuntimeException("부서를 찾을 수 없습니다."));
        return computerRepository.save(Computer.toEntity(createComputer, department));
    }

    public void update(UpdateComputer updateComputer, Integer computerId) {
        Department department = departmentRepository.findById(Integer.valueOf(updateComputer.departmentId()))
                .orElseThrow(() -> new RuntimeException("부서를 찾을 수 없습니다."));

        Computer computer = computerRepository.findById(computerId)
                .orElseThrow(() -> new RuntimeException("컴퓨터를 찾을 수 없습니다."));

        computer.update(updateComputer, department);
        computerRepository.save(computer);
    }

    public void delete(int computerId) {
        Computer computer = computerRepository.findById(computerId)
                .orElseThrow(() -> new RuntimeException("컴퓨터를 찾을 수 없습니다."));
        computer.delete("Y");
        computerRepository.save(computer);
    }
}
