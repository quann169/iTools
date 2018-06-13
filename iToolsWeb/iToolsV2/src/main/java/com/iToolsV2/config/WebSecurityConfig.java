package com.iToolsV2.config;
 
import com.iToolsV2.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Autowired
    UserDetailsServiceImpl userDetailsService;
 
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        //Md5PasswordEncoder md5CryptPasswordEncoder = new Md5PasswordEncoder();
        return bCryptPasswordEncoder;
    }
 
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
 
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
 
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
 
        http.csrf().disable();
 
        http.authorizeRequests().antMatchers("/admin/orderList", "/admin/order", "/admin/accountInfo")//
                //.access("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_MANAGER')");
        		.access("hasAnyRole('ROLE_Admin', 'ROLE_SubAdmin', 'ROLE_Accounting', 'ROLE_Emp', 'ROLE_Other', 'ROLE_PutIns', 'ROLE_TakeOver', 'ROLE_UpdateReport')");
 
        //http.authorizeRequests().antMatchers("/admin/product").access("hasRole('ROLE_MANAGER')");
        http.authorizeRequests().antMatchers("/admin/product").access("hasRole('ROLE_Admin', 'ROLE_SubAdmin', 'ROLE_Accounting', 'ROLE_Emp', 'ROLE_Other', 'ROLE_PutIns', 'ROLE_TakeOver', 'ROLE_UpdateReport')");
 
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
 
        http.authorizeRequests().and().formLogin()//
 
                // 
                .loginProcessingUrl("/j_spring_security_check") // Submit URL
                .loginPage("/admin/login")//
                .defaultSuccessUrl("/admin/accountInfo")//
                .failureUrl("/admin/login?error=true")//
                .usernameParameter("userName")//
                .passwordParameter("password")
 
                .and().logout().logoutUrl("/admin/logout").logoutSuccessUrl("/");
 
    }
}