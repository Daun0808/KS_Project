package com.example.ks.department.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreateDepartment(
        @NotBlank @Size(max = 15) String department_name
        ,@NotBlank @Size(max = 5) String department_floor
        ,@NotBlank @Size(max = 1) String delete
                               ) {
}
