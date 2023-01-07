package com.example.ec_mall.config;

import com.example.ec_mall.jwt.JwtAccessDeniedHandler;
import com.example.ec_mall.jwt.JwtAuthenticationEntryPoint;
import com.example.ec_mall.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @EnableWebSecurity
 * - 기본적인 web 보안을 활성화
 * - 추가적인 설정을 하려면 기존에는 WebSecurityConfigurer 를 implements 하거나 WebSecurityConfigurerAdapter를 extends
 * - 현재는 SecurityFilterChain 빈을 등록하여 사용하는 방법을 권장하고 있다.
 */
@EnableWebSecurity // 기본적인 web 보안을 활성화
@RequiredArgsConstructor
public class SecurityConfig {
    /**
     * Jwt를 사용하기 위해 구현해야 할 것은 크게 두가지
     * 1. JwtTokenProvider -> Jwt 토큰 제공
     * 2. JwtTokenFilter -> HTTP Request에서 토큰을 읽어 들여 정상 토큰이면 Security Context에 저장
     *
     * Security에 적용하기 위해 구현해야 하는 것은
     * 1. JwtSecurityConfig -> Jwt Filter를 Spring Security Filter Chain에 추가
     * 2. Spring Security 설정을 위한 Security Config
     */
    // 추가된 jwt 관련 클래스들을 security config에 추가
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //Rest Api이므로 basic auth(), csrf 보안을 사용하지 않음.
        http
                .csrf().disable()
                .httpBasic().disable();

        // Entry Points
        http
                .authorizeRequests()
                .antMatchers(
                        "/",
                        "/member/signUp",
                        "/member/signIn",
                        "/member/logout"

                ).permitAll()
                .antMatchers(HttpMethod.POST, "/product*").hasRole("SELLER")
                .antMatchers(HttpMethod.PATCH, "/product*").hasRole("SELLER")
                .antMatchers(HttpMethod.DELETE, "/product*").hasRole("SELLER")
                .antMatchers(HttpMethod.GET, "/product*").hasRole("USER")
                .antMatchers("/order*").hasRole("USER")
                .anyRequest().authenticated();

        // 세션사용하지 않음
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // jwt 예외 처리
        http
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint);

        // jwt 적용
        http.apply(new JwtSecurityConfig(jwtTokenProvider));

        return http.build();
    }
}
