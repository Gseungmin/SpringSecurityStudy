package Study.SpringSecurity.security.authetication.details;

import lombok.Getter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * WebAuthenticationDetails는 인증 과정 중 전달된 데이터를 저장
 * Authentication의 details 속성에 전달
 * */
@Getter
public class FormWebAuthenticationDetails extends WebAuthenticationDetails {

    private String secretKey;

    //검증에 적용할 파라미터 값을 받아와 저장
    public FormWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        secretKey = request.getParameter("secret_key");
    }
}
