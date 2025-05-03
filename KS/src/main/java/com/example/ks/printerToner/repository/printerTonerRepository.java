package com.example.ks.printerToner.repository;

import com.example.ks.printerToner.domain.printerToner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface printerTonerRepository extends JpaRepository<printerToner, Integer> {
    printerToner findByTonerName(String tonerName);
    printerToner findByPrintCode(String printCode);
}
