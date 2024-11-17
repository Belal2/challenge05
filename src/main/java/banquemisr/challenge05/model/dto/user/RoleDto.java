package banquemisr.challenge05.model.dto.user;

import banquemisr.challenge05.model.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleDto extends BaseDto {
    private String name;
    private Set<ActionDto> actions;
}
