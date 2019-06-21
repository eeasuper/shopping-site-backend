package com.nano.shoppingsite.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.nano.shoppingsite.repositories.UserRepository;
import com.nano.shoppingsite.services.JwtService;

@Component
public class JwtAuthenticationFilter implements Filter{
	
	@Autowired
	private UserRepository userRepository;
	
	private static final List<String> urlsNotRequiringAuth = new ArrayList<String>(
		Arrays.asList("\\/login","\\/register","\\/h2-console.*","\\/error","\\/product\\/([a-z]*[0-9]*)*","\\/product\\/[0-9]*\\/thumbnail","\\/search\\/([a-z]*[0-9]*)*")
	);
	
	private static boolean isAuthNotRequired(String uri) {
		return urlsNotRequiringAuth.stream().anyMatch(uri::matches);
	}
	
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
	           throws IOException, ServletException {
	    HttpServletRequest req = (HttpServletRequest) servletRequest;
	    String uri = req.getRequestURI().toString();
	    if(!isAuthNotRequired(uri)) {
		    Authentication authentication = JwtService
		    		.getAuthentication((HttpServletRequest) servletRequest);
		    if(authentication != null) {
		    	boolean exists = userRepository.existsByUsername(authentication.getName());
			    if(exists) {
			 	    SecurityContextHolder.getContext().setAuthentication(authentication);
			    }
		    }
	    }
	    filterChain.doFilter(servletRequest, servletResponse);
	   }  
	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {
		
	}	

}
