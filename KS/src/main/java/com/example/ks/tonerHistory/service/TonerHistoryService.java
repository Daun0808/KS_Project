package com.example.ks.tonerHistory.service;

import com.example.ks.toner.domain.Toner;
import com.example.ks.toner.repository.TonerRepository;
import com.example.ks.toner.service.TonerService;
import com.example.ks.tonerHistory.domain.TonerHistory;
import com.example.ks.tonerHistory.dto.CreateTonerHistory;
import com.example.ks.tonerHistory.dto.UpdateTonerHistory;
import com.example.ks.tonerHistory.repository.TonerHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TonerHistoryService {
    private final TonerHistoryRepository tonerHistoryRepository;
    private final TonerRepository tonerRepository;
    private final TonerService tonerService;

    @Transactional(readOnly = true)
    public List<TonerHistory> findByToner_TonerId(int tonerId) {
        return tonerHistoryRepository.findByToner_TonerId(tonerId);
    }

    public void createTonerHistory(CreateTonerHistory createTonerHistory) {
        Toner toner = tonerRepository.findById(createTonerHistory.tonerId())
                .orElseThrow(() -> new RuntimeException("토너를 찾을 수 없습니다."));

        TonerHistory tonerHistory = TonerHistory.toEntity(createTonerHistory, tonerRepository.findByTonerId(createTonerHistory.tonerId()));
        tonerHistoryRepository.save(tonerHistory);
    }

    public void updateTonerHistory(UpdateTonerHistory updateTonerHistory) {
        TonerHistory tonerHistory = tonerHistoryRepository.findById(updateTonerHistory.historyId())
                .orElseThrow(() -> new RuntimeException("토너 히스토리를 찾을 수 없습니다."));

        // 변경되기 전 값을 더해주고 변경된 값으로 계산해주는 알고리즘
        // ex) 기존 입고가 1이지만 2로 수정했으면 기존 입고 1을 현재 가지고 있는 입고에 더해주고 수정된 입고 2를 빼줌
        if (!tonerHistory.getHistoryReceived().equals(updateTonerHistory.historyReceived())){
            Toner toner = tonerRepository.findByTonerId(updateTonerHistory.tonerId());
            tonerService.tonerQuantity(toner.getTonerId(),(toner.getTonerQuantity()
                    + tonerHistory.getHistoryReceived()) - updateTonerHistory.historyReceived());
        }else if (!tonerHistory.getHistoryDelivery().equals(updateTonerHistory.historyDelivery())){
            Toner toner = tonerRepository.findByTonerId(updateTonerHistory.tonerId());
            tonerService.tonerQuantity(toner.getTonerId(), (toner.getTonerQuantity()
                    + tonerHistory.getHistoryDelivery()) - updateTonerHistory.historyDelivery());
        }
        tonerHistory.update(updateTonerHistory);
        tonerHistoryRepository.save(tonerHistory);
    }

    public TonerHistory deleteTonerHistory(int historyId) {
        TonerHistory tonerHistory = tonerHistoryRepository.findById(historyId)
                .orElseThrow(() -> new RuntimeException("토너 히스토리를 찾을 수 없습니다."));
        tonerHistory.delete("Y");
        Toner toner = tonerRepository.findByTonerId(tonerHistory.getToner().getTonerId());
        tonerService.tonerQuantity(toner.getTonerId(), (toner.getTonerQuantity()
                + tonerHistory.getHistoryDelivery()));
        return tonerHistoryRepository.save(tonerHistory);
    }

    @Transactional(readOnly = true)
    public TonerHistory getTonerHistory(int historyId) {
        return tonerHistoryRepository.findById(historyId)
                .orElseThrow(() -> new RuntimeException("토너 히스토리를 찾을 수 없습니다."));
    }
}
