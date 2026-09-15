package com.example.ks.tonerMonth.service;

import com.example.ks.tonerMonth.domain.TonerMonth;
import com.example.ks.tonerMonth.dto.CreateTonerMonth;
import com.example.ks.tonerMonth.dto.CreateTonerMonthList;
import com.example.ks.tonerMonth.repository.TonerMonthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TonerMonthService {
    private final TonerMonthRepository tonerMonthRepository;

    @Transactional(readOnly = true)
    public List<TonerMonth> findByTonerMonthDate(LocalDate historyDate) {
        return tonerMonthRepository.findByYearAndMonth(historyDate.getYear(),historyDate.getMonthValue());
    }

    @Transactional(readOnly = true)
    public TonerMonth findByYearAndMonthAndTonerName(LocalDate historyDate, String tonerName) {
        return tonerMonthRepository.findByYearAndMonthAndTonerName(historyDate.getYear(),historyDate.getMonthValue(),tonerName);
    }

    public void createTonerMonth(CreateTonerMonthList dto) {
        List<TonerMonth> existingList = findByTonerMonthDate(dto.tonerMonthList().getFirst().tonerMonthDate());
        Set<String> existingNames = existingList.stream()
                .map(TonerMonth::getTonerName)
                .collect(Collectors.toSet());

        for (CreateTonerMonth item : dto.tonerMonthList()) {
            if (existingNames.contains(item.tonerName())) {
                throw new RuntimeException("재고 마감이 이미 됐습니다.");
            }
            tonerMonthRepository.save(TonerMonth.toEntity(item));
        }
    }
}
