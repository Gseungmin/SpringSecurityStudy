package Study.SpringSecurity.ajax;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AjaxSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AjaxAuthenticationSuccessHandler authenticationSuccessHandler;
    private final AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;
    private final AjaxAccessDeniedHandler ajaxAccessDeniedHandler;

    /**
     * AjaxAuthenticationFilter 빈으로 등록
     * AjaxAuthenticationFilter가 처리할 AuthenticationManager 및 성공 실패 핸들러 등록
     */
    @Bean
    public AjaxAuthenticationFilter ajaxAuthenticationFilter() throws Exception {
        AjaxAuthenticationFilter ajaxAuthenticationFilter = new AjaxAuthenticationFilter();
        ajaxAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        ajaxAuthenticationFilter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler);
        return ajaxAuthenticationFilter;
    }

    //Ajax Provider 빈 객체 생성
    @Bean
    public AjaxAuthenticationProvider ajaxAuthenticationProvider() {
        return new AjaxAuthenticationProvider(userDetailsService, passwordEncoder);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/", "/users").permitAll()
                .antMatchers("/mypage").hasAnyRole("USER", "MANAGER", "ADMIN")
                .antMatchers("/messages").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/config").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(ajaxAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(ajaxAuthenticationProvider());

        http
                .exceptionHandling()
                .authenticationEntryPoint(new AjaxAuthenticationEntryPoint())
                .accessDeniedHandler(ajaxAccessDeniedHandler);
        return http.build();
    }
}