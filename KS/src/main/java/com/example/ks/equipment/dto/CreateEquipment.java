package com.example.ks.equipment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CreateEquipment(
        @Size(max = 30)
        String assetManagementNumber,

        @Size(max = 20)
        String equipmentType,

        @Size(max = 30)
        String assetCodeClassification,

        @Size(max = 20)
        String equipmentNumber,

        @Size(max = 30)
        String itemClassificationName,

        @Size(max = 30)
        String itemSubClassificationName,

        LocalDate insuranceApplicationDate,

        @NotBlank @Size(max = 20)
        String equipmentPlace,

        @NotNull
        LocalDate equipmentPlaceDate,

        @NotBlank @Size(max = 20)
        String equipmentName,

        @Size(max = 20)
        String equipmentMaker,

        @Size(max = 30)
        String equipmentPurpose,

        LocalDate equipmentProductDate,

        LocalDate equipmentBuyDate,

        @Size(max = 30)
        String equipmentSerialNumber,

        @Size(max = 20)
        String equipmentUniqueNumber,

        @Size(max = 50)
        String mfdsProductName,

        @Size(max = 50)
        String itemNameModel,

        @Size(max = 30)
        String permitReportNumber,

        @Size(max = 30)
        String mfdsClassificationNumber,

        @Size(max = 10)
        String mfdsGrade,

        @Size(max = 30)
        String businessPermitNumber,

        @Size(max = 20)
        String division,

        @Size(max = 20)
        String manufacturingCountry,

        @Size(max = 10)
        String manufactureYearMonth,

        @Size(max = 30)
        String manufacturingNumber,

        @Size(max = 20)
        String acquisitionType,

        @Size(max = 30)
        String purchaseSource,

        @Size(max = 20)
        String purchaseAmount,

        @Size(max = 10)
        String newUsedDivision,

        @Size(max = 30)
        String standardCode,

        @Size(max = 20)
        String equipmentPurchasePrice,

        @Size(max = 10)
        String riskLevel,

        LocalDate regularInspectionDate,

        @Size(max = 30)
        String maintenancePeriod,

        @Size(max = 30)
        String inspectionCompany,

        @Size(max = 15)
        String equipmentIp,

        @Size(max = 10)
        String equipmentPort,

        @Size(max = 20)
        String equipmentAeTitle,

        @Size(max = 15)
        String dicomIp,

        @Size(max = 10)
        String dicomPort,

        @Size(max = 20)
        String dicomAeTitle,

        @Size(max = 15)
        String worklistIp,

        @Size(max = 10)
        String worklistPort,

        @Size(max = 20)
        String worklistAeTitle,

        @Size(max = 50)
        String equipmentText,

        @NotBlank @Size(max = 1)
        String equipmentDel,

        LocalDate equipmentDelDate,

        @Size(max = 50)
        String equipmentDelText,

        @NotBlank @Size(max = 1)
        String del,

        @NotNull
        String departmentId
) {
}
