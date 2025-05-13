package com.example.ks.monitor.service;

import com.example.ks.department.domain.Department;
import com.example.ks.department.repository.DepartmentRepository;
import com.example.ks.monitor.domain.Monitor;
import com.example.ks.monitor.dto.CreateMonitor;
import com.example.ks.monitor.dto.UpdateMonitor;
import com.example.ks.monitor.repository.MonitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MonitorService {

    private final MonitorRepository monitorRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public List<Monitor> findAll() {
        return monitorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Monitor findByMonitorId(int monitorId) {
        return monitorRepository.findById(monitorId)
                .orElseThrow(() -> new RuntimeException("모니터를 찾을 수 없습니다."));
    }

    public Monitor create(CreateMonitor createMonitor) {
        Department department = departmentRepository.findById(Integer.valueOf(createMonitor.departmentId()))
                .orElseThrow(() -> new RuntimeException("부서를 찾을 수 없습니다."));
        return monitorRepository.save(Monitor.toEntity(createMonitor, department));
    }

    public void update(UpdateMonitor updateMonitor, Integer monitorId) {
        Department department = departmentRepository.findById(Integer.valueOf(updateMonitor.departmentId()))
                .orElseThrow(() -> new RuntimeException("부서를 찾을 수 없습니다."));

        Monitor monitor = monitorRepository.findById(monitorId)
                .orElseThrow(() -> new RuntimeException("모니터를 찾을 수 없습니다."));

        monitor.update(updateMonitor, department);
        monitorRepository.save(monitor);
    }

    public void delete(int monitorId) {
        Monitor monitor = monitorRepository.findById(monitorId)
                .orElseThrow(() -> new RuntimeException("모니터를 찾을 수 없습니다."));
        monitor.delete("Y");
        monitorRepository.save(monitor);
    }
}
