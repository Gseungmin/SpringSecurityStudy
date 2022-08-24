package Study.SpringSecurity.ajax;

import Study.SpringSecurity.domain.dto.MemberDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    //사용자가 해당 url로 요청했을때 필터가 작동
    public AjaxAuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        //Ajax 방식이 아니면 예외처리
        if (!isAjax(request)) {
            throw new IllegalStateException("인증 처리 불가");
        }

        MemberDto memberDto = objectMapper.readValue(request.getReader(), MemberDto.class);
        if (StringUtils.isEmpty(memberDto.getUsername()) || StringUtils.isEmpty(memberDto.getUsername())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 비어있습니다.");
        }

        // 인증 전 전달할 인증 토큰 객체 생성, ID와 PW 정보를 담고있음
        AjaxAuthenticationToken token = new AjaxAuthenticationToken(memberDto.getUsername(), memberDto.getPassword());

        //AuthenticationManager에 인증 토큰 객체 전달
        return getAuthenticationManager().authenticate(token);
    }

    //헤더 정보로 비교를 통해 Ajax 요청인지 Form 요청인지 구분
    private boolean isAjax(HttpServletRequest request) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            return true;
        }
        return false;
    }
}