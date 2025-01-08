package com.example.travanalysserver.security;


import com.example.travanalysserver.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Value("${frontend.url}")
    private String frontendUrl;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/login/**", "/oauth2/**").permitAll() // Allow public access
                        .anyRequest().permitAll() //Tillåter allt just nu. Ändra sen.
                )
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl(frontendUrl, true) // Redirect to React app after successful login
                        .userInfoEndpoint(userInfo -> userInfo
                                .userAuthoritiesMapper(this.userAuthoritiesMapper())
                        )
                )
                .csrf(AbstractHttpConfigurer::disable); // Disable CSRF for development

        return http.build();
    }


    private GrantedAuthoritiesMapper userAuthoritiesMapper() {

        return (authorities) -> {
            List<SimpleGrantedAuthority> mappedAuthorities = new ArrayList<>();


            authorities.forEach(authority -> {

                if (authority instanceof OAuth2UserAuthority oauth2UserAuthority) {

                    Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();


                    //String email = userAttributes.get("email").toString();
                    // email is not returned from Github!!! If not public email setting is turned on in your account


                  //  String login = userAttributes.get("login").toString();
                  //  System.out.println(login);

                 /*   String email = (String) userAttributes.get("email"); // Primary email
                    if (email == null) {
                        System.out.println("No email available. Did you include the user:email scope?");
                    } else {
                        System.out.println("User's email: " + email);
                    } */


                    // Map the attributes found in userAttributes
                    // to one or more GrantedAuthority's and add it to mappedAuthorities
                 /*   if(login.equals("SimNordlund")){
                        mappedAuthorities.add(new SimpleGrantedAuthority("Admin"));
                    } */


                    //Lagra nu i DB??
                }

            });
            return mappedAuthorities;
        };
    }
}
