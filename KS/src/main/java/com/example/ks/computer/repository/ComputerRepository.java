package com.example.ks.computer.repository;

import com.example.ks.computer.domain.Computer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ComputerRepository extends JpaRepository<Computer, Integer> {
}
