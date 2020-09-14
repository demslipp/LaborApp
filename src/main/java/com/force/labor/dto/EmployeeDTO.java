package com.force.labor.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.force.labor.domain.Grade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO {

    private BigInteger id;

    private String firstName;

    private String lastName;

    private String passport;

    private Grade grade;

    private Integer salaryPerHour;
}
