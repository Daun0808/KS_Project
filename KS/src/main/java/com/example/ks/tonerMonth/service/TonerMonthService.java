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
        List<TonerMonth> tonerMonth = findByTonerMonthDate(dto.tonerMonthList().getFirst().tonerMonthDate());

        for (CreateTonerMonth item : dto.tonerMonthList()) {
            String tonerName = item.tonerName();
            LocalDate date = item.tonerMonthDate();

            // 연도, 월 추출
            int year = date.getYear();
            int month = date.getMonthValue();

            // 기존 데이터 조회
            TonerMonth existing = tonerMonthRepository.findByYearAndMonthAndTonerName(year, month, tonerName);

            if (existing != null) {
                throw new RuntimeException("재고 마감이 이미 됐습니다.");
            } else {
                // 새로 저장
                TonerMonth newTonerMonth = TonerMonth.toEntity(item);
                tonerMonthRepository.save(newTonerMonth);
            }
        }
    }
}
