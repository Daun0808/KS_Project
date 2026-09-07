package com.example.ks.computerHistory.repository;

import com.example.ks.computerHistory.domain.ComputerHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComputerHistoryRepository extends JpaRepository<ComputerHistory, Integer> {
    List<ComputerHistory> findAllByComputer_ComputerIdOrderByPlaceDateDesc(int computerId);
}
