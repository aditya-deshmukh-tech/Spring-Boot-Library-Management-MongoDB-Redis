package com.library.management.LibraryManagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthSecurityConfig extends WebSecurityConfigurerAdapter {

    private final String[] PUBLIC_URLS = {
            "/authenticate",
            "/actuator/**"
    };

    private AuthEntryPoint authEntryPoint;

    private AuthFilter authFilter;

    private AuthUserDetailService authUserDetailService;

/*    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }*/


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests().antMatchers("/secure/**").hasAuthority("ADMIN")
                .and().authorizeRequests().antMatchers("/books/**").hasAuthority("NORMAL")
                .and().authorizeRequests().antMatchers(PUBLIC_URLS).permitAll()
                .anyRequest().authenticated().and()
                .httpBasic().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling().authenticationEntryPoint(authEntryPoint);
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Autowired
    public void setAuthEntryPoint(AuthEntryPoint authEntryPoint) {
        this.authEntryPoint = authEntryPoint;
    }

    @Autowired
    public void setAuthFilter(AuthFilter authFilter) {
        this.authFilter = authFilter;
    }

    @Autowired
    public void setAuthUserDetailService(AuthUserDetailService authUserDetailService) {
        this.authUserDetailService = authUserDetailService;
    }


    /*
    new way to configure security without using depricated WebSecurityConfigurerAdapter
            @Bean
            public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                    http
                        .cors().and()
                        .csrf().disable()
                        .authorizeRequests().antMatchers("/secure/register").hasAuthority("ADMIN")
                        .and().authorizeRequests().antMatchers("/url.short/*").hasAuthority("NORMAL")
                        .and().authorizeRequests().antMatchers(PUBLIC_URLS).permitAll()
                        .anyRequest().authenticated().and()
                        .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                http.addFilterBefore(urlShortAuthFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
            }
            @Bean
            public WebSecurityCustomizer webSecurityCustomizer() {
                return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
            }
        @Bean
        public AuthenticationManager authenticationManager(
                AuthenticationConfiguration authConfig) throws Exception {
            return authConfig.getAuthenticationManager();
        }
     */
}
