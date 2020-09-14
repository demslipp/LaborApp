package com.force.labor.service;

import com.force.labor.dto.EmployeeDTO;
import com.force.labor.dto.FindEmployeeDTO;
import com.force.labor.mapper.EmployeeMapper;
import com.force.labor.repository.EmployeeRepository;
import com.force.labor.repository.custom.EmployeeRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeRepositoryCustom employeeRepositoryCustom;
    private final EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);


    public EmployeeDTO create(EmployeeDTO dto) {
        return employeeMapper.employeeToDto(
                employeeRepository.save(employeeMapper.dtoToEmployee(dto)));
    }

    public List<EmployeeDTO> find(FindEmployeeDTO findEmployeeDTO) {
        if (findEmployeeDTO.getCriteria() == null) {
            return findAll();
        }
        return employeeMapper.employeeToDto(
                employeeRepositoryCustom.find(findEmployeeDTO.getCriteria(), findEmployeeDTO.getSort()));
    }

    public List<EmployeeDTO> findAll() {
        return employeeMapper.employeeToDto(
                employeeRepository.findAll());
    }

    public void deleteById(BigInteger id) {
        employeeRepository.deleteById(id);
    }

}
