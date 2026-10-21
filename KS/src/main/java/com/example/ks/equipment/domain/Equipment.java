package com.example.ks.equipment.domain;

import com.example.ks.department.domain.Department;
import com.example.ks.equipment.dto.CreateEquipment;
import com.example.ks.equipment.dto.UpdateEquipment;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "equipment")
@NoArgsConstructor
@Getter
public class Equipment {

    @Id
    @Column(name = "equipment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int equipmentId;

    // ---- 자산 기본 정보 ----
    @Column(name = "asset_management_number")
    private String assetManagementNumber; // 자산관리번호

    @Column(name = "equipment_type")
    private String equipmentType; // 장비구분

    @Column(name = "asset_code_classification")
    private String assetCodeClassification; // 자산코드분류

    @Column(name = "equipment_number")
    private String equipmentNumber; // 장비번호

    @Column(name = "item_classification_name")
    private String itemClassificationName; // 품목분류명

    @Column(name = "item_sub_classification_name")
    private String itemSubClassificationName; // 품목세분류명

    @Column(name = "insurance_application_date")
    private LocalDate insuranceApplicationDate; // 급여적용일자

    @Column(name = "equipment_place")
    private String equipmentPlace;

    @Column(name = "equipment_place_date")
    private LocalDate equipmentPlaceDate;

    @Column(name = "equipment_name")
    private String equipmentName; // 장비명 (CT, MRI, 초음파 등)

    @Column(name = "equipment_maker")
    private String equipmentMaker; // 제조사 (제조회사)

    @Column(name = "equipment_purpose")
    private String equipmentPurpose; // 용도

    @Column(name = "equipment_product_date")
    private LocalDate equipmentProductDate;

    @Column(name = "equipment_buy_date")
    private LocalDate equipmentBuyDate;

    @Column(name = "equipment_serial_number")
    private String equipmentSerialNumber; // 일련번호

    @Column(name = "equipment_unique_number")
    private String equipmentUniqueNumber;

    // ---- 식약처정보 ----
    @Column(name = "mfds_product_name")
    private String mfdsProductName; // 제품명

    @Column(name = "item_name_model")
    private String itemNameModel; // 품목명 및 형명[모델명]

    @Column(name = "permit_report_number")
    private String permitReportNumber; // 허가(신고)번호

    @Column(name = "mfds_classification_number")
    private String mfdsClassificationNumber; // 분류번호

    @Column(name = "mfds_grade")
    private String mfdsGrade; // 등급

    @Column(name = "business_permit_number")
    private String businessPermitNumber; // 업소허가번호

    @Column(name = "division")
    private String division; // 구분

    // ---- 장비제조사항 ----
    @Column(name = "manufacturing_country")
    private String manufacturingCountry; // 제조국가

    @Column(name = "manufacture_year_month")
    private String manufactureYearMonth; // 제조년월

    @Column(name = "manufacturing_number")
    private String manufacturingNumber; // 제조번호

    // ---- 구입세부사항 ----
    @Column(name = "acquisition_type")
    private String acquisitionType; // 도입형태

    @Column(name = "purchase_source")
    private String purchaseSource; // 구입처

    @Column(name = "purchase_amount")
    private String purchaseAmount; // 금액(원)

    @Column(name = "new_used_division")
    private String newUsedDivision; // 신규/중고 구분

    @Column(name = "standard_code")
    private String standardCode; // 표준코드

    @Column(name = "equipment_purchase_price")
    private String equipmentPurchasePrice; // 장비구매가

    @Column(name = "risk_level")
    private String riskLevel; // 고/저위험

    @Column(name = "regular_inspection_date")
    private LocalDate regularInspectionDate; // 정기점검 일시

    @Column(name = "maintenance_period")
    private String maintenancePeriod; // 유지보수 기간

    @Column(name = "inspection_company")
    private String inspectionCompany; // 점검업체

    // 장비 자체(로컬 AE)
    @Column(name = "equipment_ip")
    private String equipmentIp;

    @Column(name = "equipment_port")
    private String equipmentPort;

    @Column(name = "equipment_ae_title")
    private String equipmentAeTitle;

    // 영상 전송 대상 PACS
    @Column(name = "dicom_ip")
    private String dicomIp;

    @Column(name = "dicom_port")
    private String dicomPort;

    @Column(name = "dicom_ae_title")
    private String dicomAeTitle;

    // 검사 워크리스트 조회 대상(RIS/HIS)
    @Column(name = "worklist_ip")
    private String worklistIp;

    @Column(name = "worklist_port")
    private String worklistPort;

    @Column(name = "worklist_ae_title")
    private String worklistAeTitle;

    @Column(name = "equipment_text")
    private String equipmentText;

    @Column(name = "equipment_del")
    private String equipmentDel;

    @Column(name = "equipment_del_date")
    private LocalDate equipmentDelDate;

    @Column(name = "equipment_del_text")
    private String equipmentDelText;

    @Column(name = "del")
    private String del;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Builder
    public Equipment(
            int equipmentId,
            String assetManagementNumber,
            String equipmentType,
            String assetCodeClassification,
            String equipmentNumber,
            String itemClassificationName,
            String itemSubClassificationName,
            LocalDate insuranceApplicationDate,
            String equipmentPlace,
            LocalDate equipmentPlaceDate,
            String equipmentName,
            String equipmentMaker,
            String equipmentPurpose,
            LocalDate equipmentProductDate,
            LocalDate equipmentBuyDate,
            String equipmentSerialNumber,
            String equipmentUniqueNumber,
            String mfdsProductName,
            String itemNameModel,
            String permitReportNumber,
            String mfdsClassificationNumber,
            String mfdsGrade,
            String businessPermitNumber,
            String division,
            String manufacturingCountry,
            String manufactureYearMonth,
            String manufacturingNumber,
            String acquisitionType,
            String purchaseSource,
            String purchaseAmount,
            String newUsedDivision,
            String standardCode,
            String equipmentPurchasePrice,
            String riskLevel,
            LocalDate regularInspectionDate,
            String maintenancePeriod,
            String inspectionCompany,
            String equipmentIp,
            String equipmentPort,
            String equipmentAeTitle,
            String dicomIp,
            String dicomPort,
            String dicomAeTitle,
            String worklistIp,
            String worklistPort,
            String worklistAeTitle,
            String equipmentText,
            String equipmentDel,
            LocalDate equipmentDelDate,
            String equipmentDelText,
            String del,
            Department department
    ) {
        this.equipmentId = equipmentId;
        this.assetManagementNumber = assetManagementNumber;
        this.equipmentType = equipmentType;
        this.assetCodeClassification = assetCodeClassification;
        this.equipmentNumber = equipmentNumber;
        this.itemClassificationName = itemClassificationName;
        this.itemSubClassificationName = itemSubClassificationName;
        this.insuranceApplicationDate = insuranceApplicationDate;
        this.equipmentPlace = equipmentPlace;
        this.equipmentPlaceDate = equipmentPlaceDate;
        this.equipmentName = equipmentName;
        this.equipmentMaker = equipmentMaker;
        this.equipmentPurpose = equipmentPurpose;
        this.equipmentProductDate = equipmentProductDate;
        this.equipmentBuyDate = equipmentBuyDate;
        this.equipmentSerialNumber = equipmentSerialNumber;
        this.equipmentUniqueNumber = equipmentUniqueNumber;
        this.mfdsProductName = mfdsProductName;
        this.itemNameModel = itemNameModel;
        this.permitReportNumber = permitReportNumber;
        this.mfdsClassificationNumber = mfdsClassificationNumber;
        this.mfdsGrade = mfdsGrade;
        this.businessPermitNumber = businessPermitNumber;
        this.division = division;
        this.manufacturingCountry = manufacturingCountry;
        this.manufactureYearMonth = manufactureYearMonth;
        this.manufacturingNumber = manufacturingNumber;
        this.acquisitionType = acquisitionType;
        this.purchaseSource = purchaseSource;
        this.purchaseAmount = purchaseAmount;
        this.newUsedDivision = newUsedDivision;
        this.standardCode = standardCode;
        this.equipmentPurchasePrice = equipmentPurchasePrice;
        this.riskLevel = riskLevel;
        this.regularInspectionDate = regularInspectionDate;
        this.maintenancePeriod = maintenancePeriod;
        this.inspectionCompany = inspectionCompany;
        this.equipmentIp = equipmentIp;
        this.equipmentPort = equipmentPort;
        this.equipmentAeTitle = equipmentAeTitle;
        this.dicomIp = dicomIp;
        this.dicomPort = dicomPort;
        this.dicomAeTitle = dicomAeTitle;
        this.worklistIp = worklistIp;
        this.worklistPort = worklistPort;
        this.worklistAeTitle = worklistAeTitle;
        this.equipmentText = equipmentText;
        this.equipmentDel = equipmentDel;
        this.equipmentDelDate = equipmentDelDate;
        this.equipmentDelText = equipmentDelText;
        this.del = del;
        this.department = department;
    }

    public static Equipment toEntity(CreateEquipment dto, Department department) {
        return Equipment.builder()
                .assetManagementNumber(dto.assetManagementNumber())
                .equipmentType(dto.equipmentType())
                .assetCodeClassification(dto.assetCodeClassification())
                .equipmentNumber(dto.equipmentNumber())
                .itemClassificationName(dto.itemClassificationName())
                .itemSubClassificationName(dto.itemSubClassificationName())
                .insuranceApplicationDate(dto.insuranceApplicationDate())
                .equipmentPlace(dto.equipmentPlace())
                .equipmentPlaceDate(dto.equipmentPlaceDate())
                .equipmentName(dto.equipmentName())
                .equipmentMaker(dto.equipmentMaker())
                .equipmentPurpose(dto.equipmentPurpose())
                .equipmentProductDate(dto.equipmentProductDate())
                .equipmentBuyDate(dto.equipmentBuyDate())
                .equipmentSerialNumber(dto.equipmentSerialNumber())
                .equipmentUniqueNumber(dto.equipmentUniqueNumber())
                .mfdsProductName(dto.mfdsProductName())
                .itemNameModel(dto.itemNameModel())
                .permitReportNumber(dto.permitReportNumber())
                .mfdsClassificationNumber(dto.mfdsClassificationNumber())
                .mfdsGrade(dto.mfdsGrade())
                .businessPermitNumber(dto.businessPermitNumber())
                .division(dto.division())
                .manufacturingCountry(dto.manufacturingCountry())
                .manufactureYearMonth(dto.manufactureYearMonth())
                .manufacturingNumber(dto.manufacturingNumber())
                .acquisitionType(dto.acquisitionType())
                .purchaseSource(dto.purchaseSource())
                .purchaseAmount(dto.purchaseAmount())
                .newUsedDivision(dto.newUsedDivision())
                .standardCode(dto.standardCode())
                .equipmentPurchasePrice(dto.equipmentPurchasePrice())
                .riskLevel(dto.riskLevel())
                .regularInspectionDate(dto.regularInspectionDate())
                .maintenancePeriod(dto.maintenancePeriod())
                .inspectionCompany(dto.inspectionCompany())
                .equipmentIp(dto.equipmentIp())
                .equipmentPort(dto.equipmentPort())
                .equipmentAeTitle(dto.equipmentAeTitle())
                .dicomIp(dto.dicomIp())
                .dicomPort(dto.dicomPort())
                .dicomAeTitle(dto.dicomAeTitle())
                .worklistIp(dto.worklistIp())
                .worklistPort(dto.worklistPort())
                .worklistAeTitle(dto.worklistAeTitle())
                .equipmentText(dto.equipmentText())
                .equipmentDel(dto.equipmentDel())
                .equipmentDelDate(dto.equipmentDelDate())
                .equipmentDelText(dto.equipmentDelText())
                .del(dto.del())
                .department(department)
                .build();
    }

    public void update(UpdateEquipment dto, Department department) {
        this.assetManagementNumber = dto.assetManagementNumber();
        this.equipmentType = dto.equipmentType();
        this.assetCodeClassification = dto.assetCodeClassification();
        this.equipmentNumber = dto.equipmentNumber();
        this.itemClassificationName = dto.itemClassificationName();
        this.itemSubClassificationName = dto.itemSubClassificationName();
        this.insuranceApplicationDate = dto.insuranceApplicationDate();
        this.equipmentPlace = dto.equipmentPlace();
        this.equipmentPlaceDate = dto.equipmentPlaceDate();
        this.equipmentName = dto.equipmentName();
        this.equipmentMaker = dto.equipmentMaker();
        this.equipmentPurpose = dto.equipmentPurpose();
        this.equipmentProductDate = dto.equipmentProductDate();
        this.equipmentBuyDate = dto.equipmentBuyDate();
        this.equipmentSerialNumber = dto.equipmentSerialNumber();
        this.equipmentUniqueNumber = dto.equipmentUniqueNumber();
        this.mfdsProductName = dto.mfdsProductName();
        this.itemNameModel = dto.itemNameModel();
        this.permitReportNumber = dto.permitReportNumber();
        this.mfdsClassificationNumber = dto.mfdsClassificationNumber();
        this.mfdsGrade = dto.mfdsGrade();
        this.businessPermitNumber = dto.businessPermitNumber();
        this.division = dto.division();
        this.manufacturingCountry = dto.manufacturingCountry();
        this.manufactureYearMonth = dto.manufactureYearMonth();
        this.manufacturingNumber = dto.manufacturingNumber();
        this.acquisitionType = dto.acquisitionType();
        this.purchaseSource = dto.purchaseSource();
        this.purchaseAmount = dto.purchaseAmount();
        this.newUsedDivision = dto.newUsedDivision();
        this.standardCode = dto.standardCode();
        this.equipmentPurchasePrice = dto.equipmentPurchasePrice();
        this.riskLevel = dto.riskLevel();
        this.regularInspectionDate = dto.regularInspectionDate();
        this.maintenancePeriod = dto.maintenancePeriod();
        this.inspectionCompany = dto.inspectionCompany();
        this.equipmentIp = dto.equipmentIp();
        this.equipmentPort = dto.equipmentPort();
        this.equipmentAeTitle = dto.equipmentAeTitle();
        this.dicomIp = dto.dicomIp();
        this.dicomPort = dto.dicomPort();
        this.dicomAeTitle = dto.dicomAeTitle();
        this.worklistIp = dto.worklistIp();
        this.worklistPort = dto.worklistPort();
        this.worklistAeTitle = dto.worklistAeTitle();
        this.equipmentText = dto.equipmentText();
        this.equipmentDel = dto.equipmentDel();
        this.equipmentDelDate = dto.equipmentDelDate();
        this.equipmentDelText = dto.equipmentDelText();
        this.del = dto.del();
        this.department = department;
    }
}
