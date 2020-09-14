package com.force.labor.controller;

import com.force.labor.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping("/assignment")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService service;


    @GetMapping("assign/e/{employeeId}/t/{taskId}")
    public void assign(@PathVariable BigInteger employeeId, @PathVariable BigInteger taskId) {
        service.assign(employeeId, taskId);
    }

    @GetMapping("unassign/e/{employeeId}/t/{taskId}")
    public void unassign(@PathVariable BigInteger employeeId, @PathVariable BigInteger taskId) {
        service.unassign(employeeId, taskId);
    }
}
