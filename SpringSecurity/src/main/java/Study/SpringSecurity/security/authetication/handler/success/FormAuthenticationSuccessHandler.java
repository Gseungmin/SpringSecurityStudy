package Study.SpringSecurity.security.authetication.handler.success;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 인증 성공 시 적용되는 핸들러
 * */
@Component
public class FormAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    //이전 사용자의 정보를 담은 객체
    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //기본 URL을 설정한 후 savedRequest가 null일 경우 설정한 페이지로 보냄
        setDefaultTargetUrl("/");

        //사용자가 인증에 성공하기 전에 요청을 했던 정보를 담은 객체
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            //인증 성공 후 가려던 페이지로 이동
            String redirectUrl = savedRequest.getRedirectUrl();
            redirectStrategy.sendRedirect(request, response, redirectUrl);
        } else {
            //인증 성공 후 기본 설정 URL로 이동
            redirectStrategy.sendRedirect(request, response, getDefaultTargetUrl());
        }
    }
}
