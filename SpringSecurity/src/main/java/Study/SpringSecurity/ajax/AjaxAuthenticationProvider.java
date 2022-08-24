package Study.SpringSecurity.ajax;

import Study.SpringSecurity.security.authetication.userdetails.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class AjaxAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    //파라미터의 authentication은 AuthenticationManager로 부터 전달받는 객체로 ID와 PW 정보를 가짐
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        //ID검증, UserDetails 정보를 DB로부터 받아옴
        MemberContext memberContext = (MemberContext) userDetailsService.loadUserByUsername(username);

        //PW검증, AuthenticationProvider에서 검증
        if (!passwordEncoder.matches(password, memberContext.getMember().getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        //검증이 완료되면 인증 토큰 객체 생성
        AjaxAuthenticationToken authenticationToken =
                new AjaxAuthenticationToken(
                        memberContext.getMember(),
                        null,
                        memberContext.getAuthorities());

        return authenticationToken;
    }

    // 파라미터로 전달되는 authentication 타입과 AjaxAuthenticationToken 타입이 일치할때 해당 프로바이더가 인증처리 할 수 있게끔 해주는 로직
    @Override
    public boolean supports(Class<?> authentication) {
        return AjaxAuthenticationToken.class.isAssignableFrom(authentication);
    }
}