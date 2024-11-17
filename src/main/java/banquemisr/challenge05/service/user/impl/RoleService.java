package banquemisr.challenge05.service.user.impl;

import banquemisr.challenge05.model.entities.user.Action;
import banquemisr.challenge05.model.entities.user.Role;
import banquemisr.challenge05.repository.user.ActionRepository;
import banquemisr.challenge05.repository.user.RoleRepository;
import banquemisr.challenge05.service.user.RoleServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleService implements RoleServices {

    private final RoleRepository roleRepository;
    private final ActionRepository actionRepository;

    @Transactional
    public void assignActionToRole(String roleName, Set<String> actions) {
        Role role = roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(roleName);
                    return roleRepository.save(newRole);
                });
        for (String actionName : actions) {
            Action action = actionRepository.findByName(actionName)
                    .orElseGet(() -> {
                        Action newAction = new Action();
                        newAction.setName(actionName);
                        return actionRepository.save(newAction);
                    });
            role.addAction(action);
        }

        roleRepository.save(role);
    }

    public List<String> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(Role::getName)
                .toList();
    }


}
