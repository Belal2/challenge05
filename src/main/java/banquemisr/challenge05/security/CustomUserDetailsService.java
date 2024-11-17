package banquemisr.challenge05.security;

import banquemisr.challenge05.model.entities.user.User;
import banquemisr.challenge05.service.user.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserServices userServices;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userServices.getUserByName(username);
        Set<GrantedAuthority> authorities = new HashSet<>();

        authorities.addAll(
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                        .collect(Collectors.toSet()));

        authorities.addAll(
                user.getRoles().stream()
                        .flatMap(role -> role.getActions().stream())
                        .map(action -> new SimpleGrantedAuthority(action.getName()))
                        .collect(Collectors.toSet())
        );

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }


}
