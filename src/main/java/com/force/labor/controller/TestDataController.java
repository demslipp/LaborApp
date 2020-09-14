package com.force.labor.controller;

import com.force.labor.domain.Task;
import com.force.labor.service.test.TestDataHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("testdata/init")
@RequiredArgsConstructor
public class TestDataController {

    private final TestDataHelper testDataHelper;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public List<Task> generate(@RequestParam Integer empl_count, @RequestParam Integer task_count) {
        return testDataHelper.generateEmployees(empl_count, task_count);
    }
}
