package banquemisr.challenge05.controller.user;

import banquemisr.challenge05.model.dto.GenericResponse;
import banquemisr.challenge05.service.user.RoleServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleServices roleService;

    @PostMapping("/assign-actions")
    public GenericResponse<String> assignActionsToRole(
            @RequestParam String roleName,
            @RequestBody Set<String> actionNames) {

        roleService.assignActionToRole(roleName, actionNames);
        return GenericResponse.ok("Role registered successfully");
    }
}
