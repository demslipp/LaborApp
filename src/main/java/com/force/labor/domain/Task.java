package com.force.labor.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(of = "id")
@Builder
@Entity(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tasks_ids_gen")
    @SequenceGenerator(name = "tasks_ids_gen", sequenceName = "tasks_id_seq", allocationSize = 1)
    private BigInteger id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 1000)
    private String description;

    @Min(1)
    @Max(10)
    @Column(nullable = false)
    private Integer difficultyLevel;

    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.NORMAL;

    @Column
    private Integer estimatedTimeInHours;

    @Min(0)
    @Max(100)
    @Column
    private int doneInPercents;

    @Column(nullable = false)
    private LocalDateTime created;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.OPEN;

    @Column(nullable = false)
    private LocalDateTime updated;

    @Column(nullable = false)
    private Integer taskCost;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    private List<Employee> employee;

}
