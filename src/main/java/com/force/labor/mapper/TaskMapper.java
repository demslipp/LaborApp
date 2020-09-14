package com.force.labor.mapper;


import com.force.labor.domain.Task;
import com.force.labor.dto.TaskDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper
public interface TaskMapper {

    Task dtoToTask(TaskDTO origin);

    TaskDTO taskToDto(Task origin);

    List<TaskDTO> tasksToDtos(List<Task> origin);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCustomerFromDto(TaskDTO dto, @MappingTarget Task entity);

}
