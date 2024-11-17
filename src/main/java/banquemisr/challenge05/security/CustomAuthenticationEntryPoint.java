package banquemisr.challenge05.security;

import banquemisr.challenge05.exceptions.BanqueMisrException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    static void handleCustomResponse(HttpServletResponse httpServletResponse, ObjectMapper objectMapper) throws Exception {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType("application/json; charset=UTF-8");

        Map<String, Object> responseData = new LinkedHashMap<>();
        responseData.put("ok", Boolean.FALSE);
        responseData.put("errorMsg", "Invalid Token");
        responseData.put("status", HttpStatus.UNAUTHORIZED.value());

        PrintWriter writer = httpServletResponse.getWriter();
        objectMapper.writeValue(writer, responseData);
        writer.flush();
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        log.info("in Security filter class CustomAuthenticationEntryPoint");
        try {
            handleCustomResponse(response, objectMapper);
        } catch (Exception e) {
            throw new BanqueMisrException(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

}
