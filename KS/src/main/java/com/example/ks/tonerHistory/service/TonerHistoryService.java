package com.example.ks.tonerHistory.service;

import com.example.ks.toner.domain.Toner;
import com.example.ks.toner.repository.TonerRepository;
import com.example.ks.toner.service.TonerService;
import com.example.ks.tonerHistory.domain.TonerHistory;
import com.example.ks.tonerHistory.dto.CreateTonerHistory;
import com.example.ks.tonerHistory.dto.UpdateTonerHistory;
import com.example.ks.tonerHistory.repository.TonerHistoryRepository;
import com.example.ks.tonerMonth.domain.TonerMonth;
import com.example.ks.tonerMonth.dto.UpdateTonerMonth;
import com.example.ks.tonerMonth.repository.TonerMonthRepository;
import com.example.ks.tonerMonth.service.TonerMonthService;
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
    private final TonerMonthService tonerMonthService;
    private final TonerMonthRepository tonerMonthRepository;

    @Transactional(readOnly = true)
    public List<TonerHistory> findByToner_TonerId(int tonerId) {
        return tonerHistoryRepository.findByToner_TonerIdOrderByHistoryDateDesc(tonerId);
    }

    public void createTonerHistory(CreateTonerHistory createTonerHistory) {
        Toner toner = tonerRepository.findById(createTonerHistory.tonerId())
                .orElseThrow(() -> new RuntimeException("토너를 찾을 수 없습니다."));

        int received = createTonerHistory.historyReceived() != null ? createTonerHistory.historyReceived() : 0;
        int delivery = createTonerHistory.historyDelivery() != null ? createTonerHistory.historyDelivery() : 0;

        if (received <= 0 && delivery <= 0) {
            throw new RuntimeException("입고 및 출고 수량이 0 이하입니다.");
        }

        // TonerMonth 처리 (해당 연월 존재 시 갱신)
        TonerMonth tonerMonth = tonerMonthService.findByYearAndMonthAndTonerName(createTonerHistory.historyDate(),toner.getTonerName());
        if (tonerMonth != null) {
            if (tonerMonth.getTonerMonthDate().getMonthValue() != createTonerHistory.historyDate().getMonthValue())
            {
                throw new RuntimeException("이미 월 마감이 끝난 상태입니다. 직접 DB에서 수정해주세요");
            }
            tonerMonth.update(new UpdateTonerMonth(
                    tonerMonth.getTonerCurrentMonth() + received - delivery,
                    tonerMonth.getTonerMonthReceived() + received,
                    tonerMonth.getTonerMonthDelivery() + delivery
            ));
            tonerMonthRepository.save(tonerMonth);
        }else{
            throw new RuntimeException("월 재고 정리가 안됐습니다. 재고 마감 후 입력 가능합니다.");
        }

        TonerHistory tonerHistory = TonerHistory.toEntity(createTonerHistory, tonerRepository.findByTonerId(createTonerHistory.tonerId()));
        tonerHistoryRepository.save(tonerHistory);
    }

    public void updateTonerHistory(UpdateTonerHistory updateTonerHistory) {
        TonerHistory tonerHistory = tonerHistoryRepository.findById(updateTonerHistory.historyId())
                .orElseThrow(() -> new RuntimeException("토너 히스토리를 찾을 수 없습니다."));
        Toner toner = tonerService.findByTonerId(updateTonerHistory.tonerId());
        TonerMonth tonerMonth = tonerMonthService.findByYearAndMonthAndTonerName(updateTonerHistory.historyDate(),toner.getTonerName());
        if (tonerMonth != null) {
            if (tonerHistory.getHistoryDate().getMonthValue() != updateTonerHistory.historyDate().getMonthValue())
            {
                throw new RuntimeException("이미 월 마감이 끝난 상태입니다. 직접 DB에서 수정해주세요");
            }
            if (!tonerHistory.getHistoryReceived().equals(updateTonerHistory.historyReceived())){
                tonerMonth.update(new UpdateTonerMonth(
                        tonerMonth.getTonerCurrentMonth() - tonerHistory.getHistoryReceived() + updateTonerHistory.historyReceived()
                        ,tonerMonth.getTonerMonthReceived()  - tonerHistory.getHistoryReceived() + updateTonerHistory.historyReceived()
                        ,tonerMonth.getTonerMonthDelivery()
                        ));
                tonerMonthRepository.save(tonerMonth);
            }else if (!tonerHistory.getHistoryDelivery().equals(updateTonerHistory.historyDelivery())){
                tonerMonth.update(new UpdateTonerMonth(
                        tonerMonth.getTonerCurrentMonth() + tonerHistory.getHistoryDelivery() - updateTonerHistory.historyDelivery()
                        ,tonerMonth.getTonerMonthReceived()
                        ,tonerMonth.getTonerMonthDelivery() + updateTonerHistory.historyDelivery() - tonerHistory.getHistoryDelivery()
                ));
                tonerMonthRepository.save(tonerMonth);
            }
        }else {
            throw new RuntimeException("월 재고 정리가 안됐습니다. 재고 마감 후 입력 가능합니다.");
        }
        tonerHistory.update(updateTonerHistory);
        tonerHistoryRepository.save(tonerHistory);
    }

    public TonerHistory deleteTonerHistory(int historyId) {
        TonerHistory tonerHistory = tonerHistoryRepository.findById(historyId)
                .orElseThrow(() -> new RuntimeException("토너 히스토리를 찾을 수 없습니다."));
        tonerHistory.delete("Y");
        Toner toner = tonerService.findByTonerId(tonerHistory.getToner().getTonerId());
        TonerMonth tonerMonth = tonerMonthService.findByYearAndMonthAndTonerName(tonerHistory.getHistoryDate(), toner.getTonerName());

        if (tonerMonth != null) {
            int received = tonerHistory.getHistoryReceived() != null ? tonerHistory.getHistoryReceived() : 0;
            int delivery = tonerHistory.getHistoryDelivery() != null ? tonerHistory.getHistoryDelivery() : 0;

            // 삭제 전 재고, 입출고 수량 보정
            tonerMonth.update(new UpdateTonerMonth(
                    tonerMonth.getTonerCurrentMonth() - received + delivery, // 재고 복구
                    tonerMonth.getTonerMonthReceived() - received,
                    tonerMonth.getTonerMonthDelivery() - delivery
            ));
            tonerMonthRepository.save(tonerMonth);
        }else {
            throw new RuntimeException("월 재고 정리가 안됐습니다. 재고 마감 후 입력 가능합니다.");
        }

        return tonerHistoryRepository.save(tonerHistory);
    }

    @Transactional(readOnly = true)
    public TonerHistory getTonerHistory(int historyId) {
        return tonerHistoryRepository.findById(historyId)
                .orElseThrow(() -> new RuntimeException("토너 히스토리를 찾을 수 없습니다."));
    }
}
