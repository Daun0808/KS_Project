package com.example.ks.monitor.repository;

import com.example.ks.monitor.domain.Monitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitorRepository extends JpaRepository<Monitor, Integer> {
}
