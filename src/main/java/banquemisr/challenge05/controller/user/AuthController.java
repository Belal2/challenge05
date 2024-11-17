package banquemisr.challenge05.controller.user;

import banquemisr.challenge05.exceptions.BanqueMisrException;
import banquemisr.challenge05.model.dto.GenericResponse;
import banquemisr.challenge05.model.dto.user.JwtResponse;
import banquemisr.challenge05.model.dto.user.LoginRequest;
import banquemisr.challenge05.model.dto.user.SignupRequest;
import banquemisr.challenge05.security.JwtUtils;
import banquemisr.challenge05.service.user.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @PostMapping("/signup")
    public GenericResponse<String> signup(@RequestBody SignupRequest signupRequest) {
        userService.registerUser(signupRequest.getUsername(), signupRequest.getPassword(), signupRequest.getRoles());
        return GenericResponse.ok("User registered successfully");
    }

    @PostMapping("/login")
    public GenericResponse<JwtResponse> login(@RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateToken(loginRequest.getUsername());
            JwtResponse jwtResponse = new JwtResponse(jwt);

            return GenericResponse.ok(jwtResponse);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            throw new BanqueMisrException("bad.credentials", HttpStatus.BAD_REQUEST);
        }
    }


}
