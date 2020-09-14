package com.force.labor.service.test;

import com.force.labor.domain.Employee;
import com.force.labor.domain.Grade;
import com.force.labor.domain.Task;
import com.force.labor.domain.TaskStatus;
import com.force.labor.repository.EmployeeRepository;
import com.force.labor.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class TestDataHelper {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    public List<Task> generateEmployees(int eLimit, int tLimit) {
        var empls = Stream.generate(this::getRandomEmployee).limit(eLimit)
                .peek(employeeRepository::save)
                .collect(toList());
        var tasks = generateRandomTaskNumber(tLimit);
        tasks.forEach(task -> task.setEmployee(
                empls.stream()
                        .skip(TestHelper.getRandomInt(0, empls.size()))
                        .collect(Collectors.toUnmodifiableList())));
        return taskRepository.saveAll(tasks);
    }

    public List<Task> generateRandomTaskNumber(int limit) {
        return Stream.generate(this::getRandomTask)
                .limit(TestHelper.getRandomInt(1, limit))
                .collect(toList());

    }

    public Employee getRandomEmployee() {
        Grade grade = TestHelper.getRandomGrade();
        int salary;
        if (grade.equals(Grade.JUNIOR)) {
            salary = TestHelper.getRandomInt(10, 100);
        } else if (grade.equals(Grade.MIDDLE)) {
            salary = TestHelper.getRandomInt(100, 500);
        } else {
            salary = TestHelper.getRandomInt(500, 1000);
        }
        return Employee.builder()
                .firstName(TestHelper.getRandomLetters(10))
                .lastName(TestHelper.getRandomLetters(12))
                .passport(TestHelper.getRandomEightDigits())
                .grade(grade)
                .salaryPerHour(salary)
                .build();
    }

    public Task getRandomTask() {
        var now = LocalDateTime.now(Clock.systemUTC());
        return Task.builder()
                .created(now)
                .description(TestHelper.getRandomLetters(60))
                .difficultyLevel(TestHelper.getRandomInt(1, 3))
                .title(TestHelper.getRandomLetters(10))
                .status(TaskStatus.OPEN)
                .estimatedTimeInHours(TestHelper.getRandomInt(1, 48))
                .updated(now)
                .priority(TestHelper.getRandomPriority())
                .taskCost(TestHelper.getRandomInt(100, 100000))
                .doneInPercents(TestHelper.getRandomInt(0, 60))
                .build();
    }
}
