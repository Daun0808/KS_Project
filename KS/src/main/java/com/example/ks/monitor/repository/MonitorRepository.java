package com.example.ks.monitor.repository;

import com.example.ks.monitor.domain.Monitor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitorRepository extends JpaRepository<Monitor, Integer> {
    @EntityGraph(attributePaths = "department")
    List<Monitor> findAll();
}
