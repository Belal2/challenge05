package banquemisr.challenge05.service.user.impl;

import banquemisr.challenge05.exceptions.BanqueMisrException;
import banquemisr.challenge05.model.dto.user.RoleDto;
import banquemisr.challenge05.model.entities.user.Role;
import banquemisr.challenge05.model.entities.user.User;
import banquemisr.challenge05.repository.user.RoleRepository;
import banquemisr.challenge05.repository.user.UserRepository;
import banquemisr.challenge05.service.user.UserServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserServices {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void assignRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BanqueMisrException("usr.not.found", HttpStatus.BAD_REQUEST));

        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new BanqueMisrException("rol.not.found", HttpStatus.BAD_REQUEST));

        user.addRole(role);
        userRepository.save(user);
    }

    public User getUserByName(String userName) {
        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new BanqueMisrException("usr.not.found", HttpStatus.BAD_REQUEST));
    }

    @Override
    public void registerUser(String username, String password, Set<RoleDto> roles) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new BanqueMisrException("user.exist", HttpStatus.BAD_REQUEST);
        }
        Set<Role> roless = new HashSet<>();
        for (RoleDto roleDto : roles) {
            Optional<Role> role = roleRepository.findByName(roleDto.getName());
            if (role.isEmpty()) {
                throw new BanqueMisrException("rol.not.found", HttpStatus.BAD_REQUEST);
            }
            roless.add(role.get());
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(roless);

        userRepository.save(user);
    }


}
