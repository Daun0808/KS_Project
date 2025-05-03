package com.example.ks.printerToner.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "printer_toner")
@NoArgsConstructor
@Getter
public class printerToner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "print_toner_id")
    private int printTonerId;

    @Column(name = "print_code")
    private String printCode;

    @Column(name = "toner_name")
    private String tonerName;
}
