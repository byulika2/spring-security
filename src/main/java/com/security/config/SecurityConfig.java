package com.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터 체인에 등롣됨
@EnableMethodSecurity(securedEnabled = true) // secured 어노테이션
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable() //csrf 토큰 비활성화 (테스트 시 걸어두는 게 좋음)
        .authorizeRequests()
        .antMatchers("/user/**").authenticated() // 해당 주소로 들어오면 인증이 필요함, 인증만되면 들어갈수있음
        .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
        .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
        .anyRequest().permitAll() //이 3개 주소가 아니라면 권한이 허용된다.
        .and()
        .formLogin()
        .loginPage("/login-form")
//        .usernameParameter("") // username 파라미터 name 변경시 사용
        .loginProcessingUrl("/login")// 로그인 주소 호출시 시큐리티에서 로그인을 진행해즘
        .defaultSuccessUrl("/");

  }
}
