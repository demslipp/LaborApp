package com.force.labor.service;

import com.force.labor.domain.Employee;
import com.force.labor.domain.Grade;
import com.force.labor.domain.Task;
import com.force.labor.repository.EmployeeRepository;
import com.force.labor.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;
import java.math.BigInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssignmentService {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;


    public void assign(BigInteger employeeId, BigInteger taskId) {
        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityExistsException(String.format("Task with id %s not found!", taskId)));
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityExistsException(String.format("Employee with id %s not found!", employeeId)));

        if (validate(employee, task)) {
            task.getEmployee().add(employee);
            taskRepository.save(task);
            log.info("Employee {} successfully assigned to task {} ", employeeId, taskId);

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Task with complexity %s cannot be assigned to employee with level %s !",
                            task.getDifficultyLevel(),
                            employee.getGrade()));
        }

    }

    public void unassign(BigInteger employeeId, BigInteger taskId) {
        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityExistsException(String.format("Task with id %s not found!", taskId)));
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityExistsException(String.format("Employee with id %s not found!", employeeId)));
        task.getEmployee().remove(employee);
        taskRepository.save(task);
        log.info("Employee {} successfully unassigned from task {} ", employeeId, taskId);
    }


    private boolean validate(Employee employee, Task task) {
        var complexity = task.getDifficultyLevel();
        var grade = employee.getGrade();
        if (grade.equals(Grade.JUNIOR) && complexity < 4) {
            return true;
        } else if (grade.equals(Grade.MIDDLE) && complexity < 7) {
            return true;
        } else return grade.equals(Grade.SENIOR);

    }
}
