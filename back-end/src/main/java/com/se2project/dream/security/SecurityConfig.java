package com.se2project.dream.security;

import com.se2project.dream.filter.CustomAuthenticationFilter;
import com.se2project.dream.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration @EnableWebSecurity @RequiredArgsConstructor

/**
 * The SecurityConfig class extends the WebSecurityConfigurerAdapter
 * and it is used to handle the authentication and authorization processes.
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     *This function is used to configure the userDetailsService and the passwordEncoder.
      * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     * This function is used to configure the http calls depending on the authorization
     * characteristics of the user.
      * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //CORS POLICY!!!!
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        ////!!!!

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**", "/api/token/refresh/**", "/api/signUpFarmer/**", "/api/signUpAgronomist/**", "/api/dev/**", "/api/logout/**").permitAll();

        http.authorizeRequests().antMatchers(GET, "/api/farmer/**").hasAnyAuthority("Farmer");
        http.authorizeRequests().antMatchers(POST, "/api/farmer/**").hasAnyAuthority("Farmer");
        http.authorizeRequests().antMatchers(PUT, "/api/farmer/**").hasAnyAuthority("Farmer");
        http.authorizeRequests().antMatchers(DELETE, "/api/farmer/**").hasAnyAuthority("Farmer");


        http.authorizeRequests().antMatchers(GET, "/api/agronomist/**").hasAnyAuthority("Agronomist");
        http.authorizeRequests().antMatchers(POST, "/api/agronomist/**").hasAnyAuthority("Agronomist");
        http.authorizeRequests().antMatchers(PUT, "/api/agronomist/**").hasAnyAuthority("Agronomist");
        http.authorizeRequests().antMatchers(DELETE, "/api/agronomist/**").hasAnyAuthority("Agronomist");

        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        //http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
    }

    /**
    * This function is used to handle the authenticationManagerBean.
    */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
