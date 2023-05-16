package com.example.securityhibernate.config;

import com.example.securityhibernate.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfig {

    @Autowired
    private CustomOAuth2UserService oauthUserService;

    @Autowired
    private CustomAuthenticationProvider authProvider;

    @Autowired
    private CustomFilterJwt customFilterJwt;

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authProvider);

        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().cors()
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                                    // Link api
                    .antMatchers("/api/v1/login/**", "/api/v1/signup/**", "/api/v1/forgot/**",
                            "/api/v1/food/**", "/api/v1/food-detail/**",
                            "/api/v1/restaurant/**", "/api/v1/restaurant-detail/**",

                                    // Link resources
                            "/401", "/403", "/404", "/signup", "/login", "/forgot", "/forgot-otp", "/home", "/otp",
                            "/food", "/restaurant", "/restaurant-detail", "/cart", "/checkout", "/food-detail", "/invoice",
                            "/manager", "/manager-food-detail", "/manager-food-add", "/manager-invoice-detail", "/manager-promotion-detail")
                        .permitAll()

                    .antMatchers("/", "/resources/**", "/static/**", "/oauth/**", "/cdn-cgi/**",
                            "/**/*.css.map", "/**/*.css", "/**/*.js","/**/*.js.map", "/","/**/*.png","/**/*.jpg", "/**/*.woff2")
                        .permitAll()
                    .antMatchers("/api/v1/admin/**")
                        .hasRole("ADMIN")
                    .antMatchers("/api/v1/manager/**")
                        .hasAnyRole("ADMIN", "MANAGER")
                    .anyRequest().authenticated()
                .and()
                    .oauth2Login().
                    loginPage("/login")
                    .userInfoEndpoint()
                    .userService(oauthUserService)
                .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler());
//                .defaultSuccessUrl("/home");

        httpSecurity.addFilterBefore(customFilterJwt, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public CustomOAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new CustomOAuth2AuthenticationSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
