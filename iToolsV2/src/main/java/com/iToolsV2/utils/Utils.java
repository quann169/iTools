package com.iToolsV2.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class Utils {
	public static UserDetails getLoginUser() {
		UserDetails result = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            result = (UserDetails) principal;
        }
        return result;
    }
}
