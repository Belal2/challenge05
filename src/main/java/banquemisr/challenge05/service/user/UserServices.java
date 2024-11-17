package banquemisr.challenge05.service.user;

import banquemisr.challenge05.model.dto.user.RoleDto;
import banquemisr.challenge05.model.entities.user.User;

import java.util.Set;

public interface UserServices {
    void assignRoleToUser(String username, String roleName);

    User getUserByName(String userName);

    void registerUser(String username, String password, Set<RoleDto> roles);
}
