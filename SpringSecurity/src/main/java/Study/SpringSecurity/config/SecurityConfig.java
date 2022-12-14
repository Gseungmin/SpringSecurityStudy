package Study.SpringSecurity.config;

import Study.SpringSecurity.security.authetication.handler.failure.FormAccessDeniedHandler;
import Study.SpringSecurity.security.authetication.oauth.CustomOAuth2UserService;
import Study.SpringSecurity.security.authetication.provider.FormAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final AuthenticationDetailsSource authenticationDetailsSource;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    /**
     * AuthenticationProvider Bean ??????
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new FormAuthenticationProvider(userDetailsService, passwordEncoder());
    }

    /**
     * AccessDeniedHandler Bean ??????
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        FormAccessDeniedHandler accessDeniedHandler = new FormAccessDeniedHandler();
        accessDeniedHandler.setErrorPage("/denied");
        return accessDeniedHandler;
    }

    /**
     * PasswordEncoder Bean ??????
     * ???????????? ?????? ????????? ???????????? ??????
     * */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * WebSecurityCustomizer Bean ??????
     * WebIgnore??? ?????? ?????? ???????????? ?????? ????????? ????????? ????????? ?????????.
     * */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /**
     * WebSecurityConfigurerAdapter ??? deprecated ????????? ???????????? ??????
     * SecurityFilterChain Bean ??????
     * */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //?????? ??????
        http
                .authorizeRequests()
                .antMatchers("/", "/users", "/login").permitAll()
                .antMatchers("/mypage").hasAnyRole("USER", "MANAGER", "ADMIN")
                .antMatchers("/messages").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/config").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login_proc")
                .authenticationDetailsSource(authenticationDetailsSource)
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll();

        http
                .oauth2Login()
                .loginPage("/oauth")
                .failureHandler(authenticationFailureHandler)
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        //?????? ?????? ??????
        http
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler());
        return http.build();
    }
}
