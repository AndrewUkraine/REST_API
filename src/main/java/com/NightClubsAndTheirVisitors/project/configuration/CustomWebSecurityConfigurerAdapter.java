package com.NightClubsAndTheirVisitors.project.configuration;

import com.NightClubsAndTheirVisitors.project.MyBasicAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
/*@EnableWebSecurity*/
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private final MyBasicAuthenticationEntryPoint authenticationEntryPoint;

    public CustomWebSecurityConfigurerAdapter(MyBasicAuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

   @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1").password(passwordEncoder().encode("user1"))
                .authorities("UNKNOWN_USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and().rememberMe()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint);

        http.csrf().disable();
        http.headers().frameOptions().disable();

      /*  http.addFilterAfter(new CustomFilter(),
                BasicAuthenticationFilter.class);*/
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

