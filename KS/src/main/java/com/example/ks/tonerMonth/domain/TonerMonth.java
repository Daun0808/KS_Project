package com.example.ks.tonerMonth.domain;

import com.example.ks.tonerMonth.dto.CreateTonerMonth;
import com.example.ks.tonerMonth.dto.UpdateTonerMonth;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "toner_month")
@NoArgsConstructor
@Getter
public class TonerMonth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "toner_month_id")
    private int tonerMonthId;

    @Column(name = "toner_name", nullable = false, length = 20)
    private String tonerName;

    @Column(name = "toner_month_date", nullable = false)
    private LocalDate tonerMonthDate;

    @Column(name = "toner_previous_month", nullable = false)
    private int tonerPreviousMonth;

    @Column(name = "toner_current_month", nullable = false)
    private int tonerCurrentMonth;

    @Column(name = "toner_month_received", nullable = false)
    private int tonerMonthReceived;

    @Column(name = "toner_month_delivery", nullable = false)
    private int tonerMonthDelivery;

    @Builder
    public TonerMonth(String tonerName, LocalDate tonerMonthDate, int tonerPreviousMonth, int tonerCurrentMonth,
                      int tonerMonthReceived, int tonerMonthDelivery) {
        this.tonerName = tonerName;
        this.tonerMonthDate = tonerMonthDate;
        this.tonerPreviousMonth = tonerPreviousMonth;
        this.tonerCurrentMonth = tonerCurrentMonth;
        this.tonerMonthReceived = tonerMonthReceived;
        this.tonerMonthDelivery = tonerMonthDelivery;
    }

    public static TonerMonth toEntity(CreateTonerMonth dto) {
        return TonerMonth.builder()
                .tonerName(dto.tonerName())
                .tonerMonthDate(dto.tonerMonthDate())
                .tonerPreviousMonth(dto.tonerPreviousMonth())
                .tonerCurrentMonth(dto.tonerCurrentMonth())
                .tonerMonthReceived(dto.tonerMonthReceived())
                .tonerMonthDelivery(dto.tonerMonthDelivery())
                .build();
    }

    public void update(UpdateTonerMonth dto) {
        this.tonerCurrentMonth = dto.tonerCurrentMonth();
        this.tonerMonthReceived = dto.tonerMonthReceived();
        this.tonerMonthDelivery = dto.tonerMonthDelivery();
    }

    public void duplication(CreateTonerMonth dto) {
        this.tonerPreviousMonth = dto.tonerPreviousMonth();
        this.tonerCurrentMonth = dto.tonerCurrentMonth();
        this.tonerMonthReceived = dto.tonerMonthReceived();
        this.tonerMonthDelivery = dto.tonerMonthDelivery();
        this.tonerName = dto.tonerName();
        this.tonerMonthDate = dto.tonerMonthDate();
    }
}
