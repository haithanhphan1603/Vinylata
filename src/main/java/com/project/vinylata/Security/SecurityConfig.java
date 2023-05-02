package com.project.vinylata.Security;

import com.project.vinylata.Security.jwt.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String[] UN_SECURED_URLs = {"/login", "/register", "/api/products/", "/api/products/{productId}", "/api/categories/", "/api/categories/{categoryId}", "/api/vendors/", "/api/vendors/{vendorId}"};

    private static final String[] USER_SECURED_URLs = {"/api/cart/**", "/logout",
            "/api/order/user/**", "/api/voucher/user/**", };

    private static final String[] ADMIN_SECURED_URLs = {"/api/users/**", "/logout",
            "/api/products/**", "/api/products/admin/del/{productId}", "/api/products/admin/add/**", "/api/products/admin/update/{productId}/**",
            "/api/order/admin/**",
            "/api/voucher/admin/**",
            "/api/vendors/**", "/api/vendors/admin/update/{vendorId}", "/api/vendors/admin/add","/api/vendors/admin/del/{vendorId}",
            "/api/categories/**", "/api/categories/admin/add", "/api/admin/categories/update/{categoryId}", "/api/admin/categories/del/{categoryId}"};

    @Autowired
    private JWTAuthenticationFilter authenticationFilter;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private LogoutHandler logoutHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(((request, response, authentication) -> {
                    SecurityContextHolder.clearContext();
                    response.setStatus(HttpStatus.OK.value());
                }
                ));

        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(UN_SECURED_URLs).permitAll().and()
                .authorizeHttpRequests().requestMatchers(ADMIN_SECURED_URLs)
                .hasAuthority("ROLE_ADMIN").
                and()
                .authorizeHttpRequests().requestMatchers(USER_SECURED_URLs)
                .hasAuthority("ROLE_USER")
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

}
