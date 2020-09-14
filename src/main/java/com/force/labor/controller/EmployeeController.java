package com.force.labor.controller;

import com.force.labor.dto.EmployeeDTO;
import com.force.labor.dto.FindEmployeeDTO;
import com.force.labor.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ResponseBody
    public EmployeeDTO create(@Validated @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.create(employeeDTO);
    }

    @GetMapping("/all")
    @ResponseBody
    public List<EmployeeDTO> findAllEmployees() {
        return employeeService.findAll();
    }

    @PostMapping("/search")
    @ResponseBody
    public List<EmployeeDTO> find(@Validated @RequestBody FindEmployeeDTO findEmployeeDTO) {
        return employeeService.find(findEmployeeDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        employeeService.deleteById(id);
    }
}

