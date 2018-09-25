package com.corgo.authentication;

import java.io.IOException;
import java.security.Principal;
import java.util.NoSuchElementException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.corgo.DTO.UserDTO;
import com.corgo.service.UserService;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler  {
	
	@Autowired
	private final UserService service;
	
	
	
	CustomAuthenticationSuccessHandler(UserService service) {
		this.service = service;
	}
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	                                    Authentication authentication) throws IOException, ServletException {
		//implementation
				//System.out.println(request);
				//System.out.println(response);
				//String targetUrl = "/api/newUser";
				//System.out.println(authentication);
				Facebook facebook = new FacebookTemplate(((OAuth2AuthenticationDetails)((OAuth2Authentication) authentication).getDetails()).getTokenValue());
				String [] fields = { "id", "email",  "first_name", "last_name" };
				User userProfile = facebook.fetchObject("me", User.class, fields);
				
				
				System.out.println(userProfile.getFirstName());
				System.out.println(authentication.getName());

				//System.out.println(userProfile);
				//System.out.println(userProfile.getId());
				
				try {
					if (service.findByUserId(authentication.getName()) != null) {
						System.out.println("we already have an account!");
						System.out.println(userProfile.getId());
						redirectStrategy.sendRedirect(request, response, "/success");
					}
				} catch(NoSuchElementException e) {
					System.out.println("in custom authentication ");

					
					UserDTO partialUser = new UserDTO();
					partialUser.setUserId(authentication.getName());
					partialUser.setName(userProfile.getFirstName() + " " + userProfile.getLastName());
					partialUser.setEmail(userProfile.getEmail());
					
					System.out.println(service.create(partialUser));

					
					redirectStrategy.sendRedirect(request, response, "/newuser");
					
				}
	}
}
