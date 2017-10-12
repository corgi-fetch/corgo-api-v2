package com.corgo.authentication;

import java.security.Principal;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import javax.persistence.*;

import com.corgo.service.UserService;

@Component
public class AuthenticationListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {
	
	@Autowired
	private final UserService service;
	
	AuthenticationListener(UserService service) {
		this.service = service;
	}
	
	@Override
	public void onApplicationEvent(final InteractiveAuthenticationSuccessEvent event) {
		Authentication principal = SecurityContextHolder.getContext().getAuthentication();
		
		System.out.println(SecurityContextHolder.getContext());
		/*if (service.findById(principal) == null) {
			System.out.println("There is no account here!");
		}*/
		try {
			if (service.findById(principal.getName()) != null) {
				System.out.println(principal);
			}
		} catch(NoSuchElementException e) {
			System.out.println("no user found");
			System.out.println("new user!");
		}
	
	}
	

}
