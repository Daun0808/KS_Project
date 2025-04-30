package com.example.ks.toner.service;

import com.example.ks.toner.domain.Toner;
import com.example.ks.toner.dto.CreateToner;
import com.example.ks.toner.dto.UpdateToner;
import com.example.ks.toner.repository.TonerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TonerService {
    private final TonerRepository tonerRepository;

    public Toner findByTonerName(String tonerName) {
        return tonerRepository.findByTonerName(tonerName);
    }

    public Toner findByTonerId(Integer tonerId) {
        return tonerRepository.findByTonerId(tonerId);
    }

    public List<Toner> findAll() {
        return tonerRepository.findAll();
    }

    public void createToner(CreateToner createToner) {
        Toner toner = Toner.toEntity(createToner);
        tonerRepository.save(toner);
    }

    public void updateToner(UpdateToner updateToner) {
        Toner toner = tonerRepository.findByTonerId(updateToner.tonerId());
        if (toner == null) {
            throw new RuntimeException("토너를 찾을 수 없습니다. ID: " + updateToner.tonerId());
        }
        toner.update(updateToner);
        tonerRepository.save(toner);
    }

    public void tonerQuantity(Integer tonerId, Integer quantity) {
        Toner toner = tonerRepository.findByTonerId(tonerId);
        if (toner == null) {
            throw new RuntimeException("토너를 찾을 수 없습니다. ID: " + tonerId);
        }
        toner.updateQuantity(quantity);
        tonerRepository.save(toner);
    }


}
