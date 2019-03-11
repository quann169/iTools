package com.iToolsV2.service;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.iToolsV2.dao.AssessorDAO;
import com.iToolsV2.dao.RolesDAO;
import com.iToolsV2.entity.Assessor;
 
@Service
@DependsOn("assessorDao")
public class UserDetailsServiceImpl implements UserDetailsService {
 
   /* @Autowired
    private AccountDAO accountDAO;*/
	String ROLE_PREFIX = "ROLE_";
    
    @Autowired
    private AssessorDAO asssessorDAO;
    
    @Autowired
    private RolesDAO rolesDAO;
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*Account account = accountDAO.findAccount(username);        
        System.out.println("Account= " + account);*/
    	Assessor asssessor = asssessorDAO.findAccount(username.toLowerCase());
        System.out.println("Account= " + asssessor);
        
        /*if (account == null) {
            throw new UsernameNotFoundException("User " //
                    + username + " was not found in the database");
        }*/
        if (asssessor == null) {
            throw new UsernameNotFoundException("User " //
                    + username + " was not found in the database");
        }
 
        // EMPLOYEE,MANAGER,..
        //String role = account.getUserRole();
        List<String> roleNames = this.rolesDAO.getRoleNames(asssessor.getAssessorID());
 
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
 
        // ROLE_EMPLOYEE, ROLE_MANAGER
        /*GrantedAuthority authority = new SimpleGrantedAuthority(role);
        grantList.add(authority);*/
        
        if (roleNames != null) {
            for (String role : roleNames) {
                // ROLE_USER, ROLE_ADMIN,..
            	//if(role.equals("Admin")) {
            	//	role = "ROLE_MANAGER";
            	role = ROLE_PREFIX + role;
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            	//}
            }
        }
 
        //boolean enabled = account.isActive();
        boolean enabled = asssessor.isActive();
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = !asssessor.isLocked();
 
        /*UserDetails userDetails = (UserDetails) new User(account.getUserName(), //
                account.getEncrytedPassword(), enabled, accountNonExpired, //
                credentialsNonExpired, accountNonLocked, grantList);*/
        UserDetails userDetails = (UserDetails) new User(asssessor.getUserName(), //
        		asssessor.getEncrytedPassword(), enabled, accountNonExpired, //
                credentialsNonExpired, accountNonLocked, grantList);
 
        return userDetails;
    }
 
}