package com.force.labor.dto;

import com.force.labor.domain.Grade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.math.BigInteger;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindEmployeeDTO {

    private ECriteria criteria;
    private Sort sort;

    @Data
    public static class ECriteria {
        private List<BigInteger> ids;
        private String firstName;
        private String lastName;
        private String passport;
        private Grade grade;
        private Integer salaryPerHourGreaterThan;
        private Integer salaryPerHourLessThan;
    }
}