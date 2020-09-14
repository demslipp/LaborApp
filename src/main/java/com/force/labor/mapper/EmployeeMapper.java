package com.force.labor.mapper;

import com.force.labor.domain.Employee;
import com.force.labor.dto.EmployeeDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {


    EmployeeDTO employeeToDto(Employee employee);

    Employee dtoToEmployee(EmployeeDTO employeeDTO);

    List<EmployeeDTO> employeeToDto(List<Employee> employees);

    List<Employee> dtoToEmployee(List<EmployeeDTO> employeeDTO);

}
