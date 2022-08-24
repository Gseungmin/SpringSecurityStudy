package Study.SpringSecurity.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        String errorMessage = "아이디또는 비밀번호가 잘못되었습니다";

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        //BadCredentialsException은 Password 에러시 발생하는 예외
        if (exception instanceof BadCredentialsException) {
            errorMessage = "wrong1";
        } //InsufficientAuthenticationException는 Id, Password 제외 유효성 에러시 발생하는 예외
        else if (exception instanceof InsufficientAuthenticationException) {
            errorMessage = "wrong2";
        }

        objectMapper.writeValue(response.getWriter(), errorMessage);
    }
}
