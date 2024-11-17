package banquemisr.challenge05.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class SignupRequest {
    private String username;
    private String password;
    private Set<RoleDto> roles;
}
