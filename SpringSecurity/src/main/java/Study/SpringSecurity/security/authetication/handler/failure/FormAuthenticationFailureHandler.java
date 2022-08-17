package Study.SpringSecurity.security.authetication.handler.failure;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 인증 성공 시 적용되는 핸들러
 * */
@Component
public class FormAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "";

        //Id 예외 처리
        if (exception instanceof UsernameNotFoundException){
            errorMessage = "wrong User";
        }//Pw 예외 처리
        else if (exception instanceof BadCredentialsException) {
            errorMessage = "wrong Password";
        }  //유효성 검사 예외 처리
        else if (exception instanceof InsufficientAuthenticationException) {
            errorMessage = "wrong Input";
        }

        setDefaultFailureUrl("/login?error=true&exception="+errorMessage);

        //부모 클래스에게 처리 위임
        super.onAuthenticationFailure(request, response, exception);
    }
}