package com.example.ks.toner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreateToner(

        @NotBlank
        @Size(max = 15)
        String tonerCode,

        @NotBlank
        @Size(max = 20)
        String tonerName,

        @Size(max = 20)
        String tonerText,

        @NotBlank
        @Size(max = 10)
        String tonerUnit,

        @NotNull
        Integer tonerQuantity,

        @NotBlank
        @Size(max = 1)
        String del
) {}
