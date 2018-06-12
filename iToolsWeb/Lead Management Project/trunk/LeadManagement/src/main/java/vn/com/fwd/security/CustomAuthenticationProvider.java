package vn.com.fwd.security;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import vn.com.fwd.domain.model.User;
import vn.com.fwd.domain.service.user.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Inject
	protected UserService userService;

	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		User user = userService.findUser(username);
		
		if (user == null || !user.getName().equalsIgnoreCase(username)) {
			throw new BadCredentialsException("Username not found.");
		}

		if (!password.equals(user.getPassword())) {
			throw new BadCredentialsException("Wrong password.");
		}

		List<GrantedAuthority> authorities = new ArrayList<>();
		String roleStr = user.getRoles();
		if (roleStr != null) {
			String[] roles = roleStr.split(",");
			for (String role : roles) {
				System.out.println(role);
				authorities.add(new SimpleGrantedAuthority(role));
			}
		}
		return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	public boolean supports(Class<?> arg0) {
		return true;
	}

}