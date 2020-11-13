package com.java.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.companion.JwtUtils;
import com.java.component.TokenFilter;
import com.java.domain.HttpResponse;
import com.java.domain.HttpResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    TokenFilter tokenFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/hello").permitAll().anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler((req, resp, authentication) -> {
                    String token = JwtUtils.createToken(authentication);
                    resp.setContentType("application/json;charset=utf-8");
                    resp.setHeader("Authorization", token);
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(authentication.getPrincipal()));
                    out.flush();
                    out.close();
                })
                .failureHandler((req, resp, e) -> {
                    HttpResponse.response(resp,new HttpResult(500, "登录失败！", "账号或密码错误"));
                }).permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((req, resp, authentication) -> {
                    HttpResponse.response(resp,new HttpResult(200, "注销成功！", null));
                }).permitAll()
                .and()
                .cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .accessDeniedHandler((req, resp, e) -> {
                    HttpResponse.response(resp,new HttpResult(401, "没有权限！", null));
                });
    }





}
