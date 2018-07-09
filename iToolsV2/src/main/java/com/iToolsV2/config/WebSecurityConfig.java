package com.iToolsV2.config;
 
import com.iToolsV2.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.iToolsV2.utils.MD5PasswordEncoder;
 
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Autowired
    UserDetailsServiceImpl userDetailsService;
 
    /*@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }*/
    
    @Bean
    public MD5PasswordEncoder passwordEncoder() {
    	MD5PasswordEncoder md5CryptPasswordEncoder = new MD5PasswordEncoder();        
        return md5CryptPasswordEncoder;
    }
 
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
 
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
 
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
 
        http.csrf().disable();
 
        http.authorizeRequests().antMatchers("/admin/orderList", "/admin/order", "/admin/accountInfo", "/machineList")//
                //.access("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_MANAGER')");
        		.access("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_MANAGER','ROLE_Admin', 'ROLE_SubAdmin', 'ROLE_Accounting', 'ROLE_PutIns', 'ROLE_TakeOver', 'ROLE_UpdateReport')");
 
        http.authorizeRequests().antMatchers("/admin/product", "/ctidList", "/companyList", "/userList", "/admin/registerSuccessful", "/admin/register", "/admin/assessorDetail").access("hasRole('ROLE_Admin')");
        //http.authorizeRequests().antMatchers("/admin/product").access("hasRole('ROLE_Admin')");
 
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
 
        http.authorizeRequests().and().formLogin()//
 
                // 
                .loginProcessingUrl("/j_spring_security_check") // Submit URL
                .loginPage("/admin/login")//
                .defaultSuccessUrl("/machineList")//
                //.defaultSuccessUrl("/admin/accountInfo")//
                .failureUrl("/admin/login?error=true")//
                .usernameParameter("userName")//
                .passwordParameter("password")
 
                .and().logout().logoutUrl("/admin/logout").logoutSuccessUrl("/");
 
    }
}