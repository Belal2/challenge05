package banquemisr.challenge05.mappers;

import banquemisr.challenge05.model.dto.user.ActionDto;
import banquemisr.challenge05.model.entities.user.Action;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActionMapper {
    Action toEntity(ActionDto actionDto);

    ActionDto toDto(Action action);
}
