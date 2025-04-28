package com.example.ks.toner.domain;

import com.example.ks.toner.dto.CreateToner;
import com.example.ks.toner.dto.UpdateToner;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "toner")
@NoArgsConstructor
@Getter
public class Toner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "toner_id")
    private int tonerId;

    @Column(name = "toner_code", nullable = false, length = 15)
    private String tonerCode;

    @Column(name = "toner_name", nullable = false, length = 20)
    private String tonerName;

    @Column(name = "toner_text", nullable = false, length = 20)
    private String tonerText;

    @Column(name = "toner_unit", nullable = false, length = 10)
    private String tonerUnit;

    @Column(name = "toner_quantity", nullable = false)
    private int tonerQuantity;

    @Column(name = "del", nullable = false, length = 1)
    private String del;

    @Builder
    public Toner(String tonerCode, String tonerName, String tonerText,
                 String tonerUnit, int tonerQuantity, String del) {
        this.tonerCode = tonerCode;
        this.tonerName = tonerName;
        this.tonerText = tonerText;
        this.tonerUnit = tonerUnit;
        this.tonerQuantity = tonerQuantity;
        this.del = del;
    }

    public static Toner toEntity(CreateToner dto) {
        return Toner.builder()
                .tonerCode(dto.tonerCode())
                .tonerName(dto.tonerName())
                .tonerText(dto.tonerText())
                .tonerUnit(dto.tonerUnit())
                .tonerQuantity(dto.tonerQuantity())
                .del(dto.del())
                .build();
    }

    public void update(UpdateToner dto) {
        this.tonerCode = dto.tonerCode();
        this.tonerName = dto.tonerName();
        this.tonerText = dto.tonerText();
        this.tonerUnit = dto.tonerUnit();
        this.tonerQuantity = dto.tonerQuantity();
        this.del = dto.del();
    }

}
