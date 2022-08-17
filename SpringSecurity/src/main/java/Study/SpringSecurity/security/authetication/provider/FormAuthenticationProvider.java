package Study.SpringSecurity.security.authetication.provider;

import Study.SpringSecurity.security.authetication.details.FormWebAuthenticationDetails;
import Study.SpringSecurity.security.authetication.userdetails.MemberContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@RequiredArgsConstructor
public class FormAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 검증을 위한 구현 로직
     * 파라미터의 authentication은 AuthenticationManager로부터 전달받는 객체로 Id와 Pw 정보를 가짐
     * */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("authenticate 시작");

        String loginId = authentication.getName();
        String password = (String) authentication.getCredentials();

        //DB로부터 Member 데이터 및 권한 정보 받아옴
        MemberContext memberContext = (MemberContext) userDetailsService.loadUserByUsername(loginId);

        //PW 검증 로직
        if (!passwordEncoder.matches(password, memberContext.getMember().getPassword())) {
            //BadCredentialsException은 Pw 발생 예외
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        //InsufficientAuthenticationException는 Id, Pw 이외 발생 예외
        FormWebAuthenticationDetails details = (FormWebAuthenticationDetails) authentication.getDetails();
        String secretKey = details.getSecretKey();
        if (secretKey == null || !"secret".equals(secretKey)) {
            throw new InsufficientAuthenticationException("정보가 잘못되었습니다.");
        }

        //authenticationToken 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        memberContext.getMember(),
                        null,
                        memberContext.getAuthorities());

        //AuthenticationManger에게 인증 토큰 객체 반환
        return authenticationToken;
    }

    /**
     * 파라미터로 전달되는 authentication 타입과 FormAuthenticationProvider의 사용 토큰 타입이 일치할때
     * 해당 Provider가 인증 처리를 할 수 있게 해주는 로직
     * */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
