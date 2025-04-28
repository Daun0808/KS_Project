package com.example.ks.toner.repository;

import com.example.ks.toner.domain.Toner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TonerRepository extends JpaRepository<Toner, Integer> {
    public Toner findByTonerId(Integer tonerId);
    public Toner findByTonerName(String tonerName);
}
