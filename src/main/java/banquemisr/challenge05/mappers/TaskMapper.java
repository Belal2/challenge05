package banquemisr.challenge05.mappers;

import banquemisr.challenge05.model.dto.task.TaskDTO;
import banquemisr.challenge05.model.entities.task.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toEntity(TaskDTO taskDTO);

    TaskDTO toDto(Task task);

    void updateTask(TaskDTO taskDTO, @MappingTarget Task task);
}
