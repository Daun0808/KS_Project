package com.example.ks.printerToner.service;

import com.example.ks.printerToner.domain.printerToner;
import com.example.ks.printerToner.repository.printerTonerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PrinterTonerService {
    private final printerTonerRepository printerTonerRepository;

    public String printToner(String printName) {
        printerToner printerToner = printerTonerRepository.findByPrintCode(printName);
        if (printerToner != null) {
            return printerToner.getTonerName();
        }
        return null;
    }
}
