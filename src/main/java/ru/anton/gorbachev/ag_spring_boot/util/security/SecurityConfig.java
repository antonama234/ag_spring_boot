package ru.anton.gorbachev.ag_spring_boot.util.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthProvider provider;
    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;


    @Autowired
    public SecurityConfig( AuthProvider provider, InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.provider = provider;
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inMemoryUserDetailsManager);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable()
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/reg", "/login").permitAll()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new AuthorizationCustomFilter(provider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new AuthenticationCustomFilter(inMemoryUserDetailsManager, provider), AuthorizationCustomFilter.class);
    }
}
