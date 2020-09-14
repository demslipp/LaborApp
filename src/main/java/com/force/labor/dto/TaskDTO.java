package com.force.labor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.force.labor.domain.Priority;
import com.force.labor.domain.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDTO {

    private BigInteger id;

    @NonNull
    private String title;

    private String description;

    @NonNull
    private Integer difficultyLevel;

    private Priority priority;

    private Integer estimatedTimeInHours;

    private TaskStatus status;

    private LocalDateTime updated;

    private LocalDateTime created;

    private BigInteger assignedTo;

    @NonNull
    private Integer taskCost;

    private Integer doneInPercents;

    private List<BigInteger> employees;

}
