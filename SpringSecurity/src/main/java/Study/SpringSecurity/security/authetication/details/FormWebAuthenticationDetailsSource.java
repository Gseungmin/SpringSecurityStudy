package Study.SpringSecurity.security.authetication.details;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * AuthenticationDetailsSource는 WebAuthenticationDetails 객체를 생성
 * WebAuthenticationDetails를 통해 검증할 데이터 반환받음
 * */
@Component
public class FormWebAuthenticationDetailsSource implements
        AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

    //검증할 데이터 반환 받음
    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new FormWebAuthenticationDetails(context);
    }
}
