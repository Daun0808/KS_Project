package com.example.ks.department.domain;

import com.example.ks.department.dto.request.CreateDepartment;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "department")
@NoArgsConstructor
@Getter
public class Department {
    @Id
    @Column(name = "department_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int departmentId;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "department_floor")
    private String departmentFloor;

    @Column(name = "del")
    private String delete;

    @Builder
    public Department(String Department_name, String Department_floor, String Delete) {
        this.departmentName = Department_name;
        this.departmentFloor = Department_floor;
        this.delete = Delete;
    }

    public static Department toEntity(CreateDepartment createDepartment) {
        return Department.builder()
                .Department_name(createDepartment.department_name())
                .Department_floor(createDepartment.department_floor())
                .Delete(createDepartment.delete())
                .build();
    }

    public void update(CreateDepartment createDepartment) {
        this.departmentName = createDepartment.department_name();
        this.departmentFloor = createDepartment.department_floor();
        this.delete = createDepartment.delete();
    }
}
