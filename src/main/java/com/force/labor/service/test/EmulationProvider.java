package com.force.labor.service.test;

import com.force.labor.domain.TaskStatus;
import com.force.labor.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmulationProvider {

    private final TaskRepository taskRepository;
    private final TestDataHelper testDataHelper;

//    @Scheduled(fixedDelay = 1)
    @Transactional
    public void closeTaskJob() {
        taskRepository.findAll().stream()
                .filter(task -> task.getDoneInPercents() >= 100 && !task.getStatus().equals(TaskStatus.CLOSED))
                .forEach(task -> {
                    task.setStatus(TaskStatus.CLOSED);
                    log.info("Task {}:{} finished.", task.getId(), task.getTitle());
                    var newTask = testDataHelper.generateRandomTaskNumber(1).get(0);
                    newTask.setEmployee(task.getEmployee());
                    log.info("Task {} created. Employees assigned to the task: {}", newTask.getTitle(), task.getEmployee());
                    taskRepository.save(newTask);
                    task.setEmployee(Collections.emptyList());
                    taskRepository.save(task);

                });
    }


//    @Scheduled(fixedDelay = 10000)
    @Transactional
    public void emulateWorkJob() {
        log.warn("_____________________________________________________________");
        log.warn("Emulation work job started ... ");
        taskRepository.findAll().stream().filter(task -> !task.getStatus().equals(TaskStatus.CLOSED))
                .forEach(task -> {
                    log.info("Task ID:{}, STATUS:{}, DONE:{}%", task.getId(), task.getStatus(), task.getDoneInPercents());
                    if (task.getStatus().equals(TaskStatus.OPEN)) {
                        if (task.getDoneInPercents() > 0) {
                            task.setStatus(TaskStatus.IN_PROGRESS);
                            log.info("Started to work on task: {} ", task.getId());
                        }
                    }
                    task.setDoneInPercents(task.getDoneInPercents() + TestHelper.getRandomInt(0, 15));
                    task.setUpdated(LocalDateTime.now());
                });
        log.warn("Emulation job finished ... ");
        log.warn("_____________________________________________________________");

    }

}
