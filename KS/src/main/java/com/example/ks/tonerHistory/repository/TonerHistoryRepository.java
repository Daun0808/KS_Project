package com.example.ks.tonerHistory.repository;

import com.example.ks.tonerHistory.domain.TonerHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TonerHistoryRepository extends JpaRepository<TonerHistory, Integer> {
    List<TonerHistory> findByToner_TonerId(int tonerId);

}
