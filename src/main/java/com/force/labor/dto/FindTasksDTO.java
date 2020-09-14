package com.force.labor.dto;

import com.force.labor.domain.Priority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindTasksDTO {

    private TCriteria criteria;
    private Sort sort;

    @Data
    public static class TCriteria {
        private List<BigInteger> ids;
        private Priority priority;
        private LocalDateTime createdSince;
        private LocalDateTime createdBefore;
        private Integer estimatedTimeInHours;
        @Min(0)
        @Max(100)
        private Integer doneInPercents;
        private Integer taskCostGreaterThan;
        private Integer taskCostLessThan;
        @Min(0)
        @Max(10)
        private Integer difficultyLevel;
    }
}
