package banquemisr.challenge05.service.user;

import java.util.List;
import java.util.Set;

public interface RoleServices {
    void assignActionToRole(String roleName, Set<String> actionName);

    List<String> getAllRoles();

}
