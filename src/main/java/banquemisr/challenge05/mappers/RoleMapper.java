package banquemisr.challenge05.mappers;

import banquemisr.challenge05.model.dto.user.RoleDto;
import banquemisr.challenge05.model.entities.user.Role;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toEntity(RoleDto roleDto);

    RoleDto toDto(Role role);

    Set<Role> toEntitySet(Set<RoleDto> roleDtos);
}
