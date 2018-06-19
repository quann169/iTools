package com.iToolsV2.service;
 
import org.springframework.security.core.userdetails.UserDetails;
 
public interface UserDetailsService {
    UserDetails loadUserByUsername(String userName);
}