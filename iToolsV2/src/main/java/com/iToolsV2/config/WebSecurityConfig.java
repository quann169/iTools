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
    
    @Autowired
    private CustomHandlerLogin customHandleLogin;
 
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
 
        http.authorizeRequests().antMatchers("/admin/accountInfo", "/machineList")//
        		.access("hasAnyRole('ROLE_Admin', 'ROLE_SubAdmin', 'ROLE_Accounting', 'ROLE_PutIns', 'ROLE_TakeOver', 'ROLE_UpdateReport')");
 
        http.authorizeRequests().antMatchers("/ctidList", "/companyList", "/userList", "/admin/registerSuccessful", "/admin/register", "/admin/assessorDetail").access("hasRole('ROLE_Admin')");
 
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
 
		// begin NamLP : 2018-07-14
        http.authorizeRequests().antMatchers("/css**", "/css/**", "/fonts**", "/fonts/**", "/img**", "/img/**", "/js**", "/js/**", "/vendor**", "/vendor/**", "/pdf.jpg", "/userguide.pdf", "/reset-password", "/reset-password/**").permitAll();
        http.authorizeRequests().antMatchers("/transaction", "/transaction/**").access("hasAnyRole('ROLE_Admin', 'ROLE_Accounting')");
//        http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll();
        // end NamLP : 2018-07-14
        http.authorizeRequests().and().formLogin()//
 
                // 
                .loginProcessingUrl("/j_spring_security_check") // Submit URL
                .loginPage("/admin/login").permitAll().successHandler(customHandleLogin)//
                //.defaultSuccessUrl("/machineList")//
                //.defaultSuccessUrl("/admin/accountInfo")//
                .failureUrl("/admin/login?error=true")//
                .usernameParameter("userName")//
                .passwordParameter("password")
 
                .and().logout().logoutUrl("/admin/logout").logoutSuccessUrl("/");
 
    }
}