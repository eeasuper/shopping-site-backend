package com.nano.shoppingsite.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsSiteFilter implements Filter{
	private final String frontEndDomain = "http://localhost:3000";
//	private final String frontEndDomain = "https://video-site-frontend.herokuapp.com";

@Override
public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    
//    HttpServletResponse response = (HttpServletResponse) res;
//    HttpServletRequest request = (HttpServletRequest) req;
//
//    response.setHeader("Access-Control-Allow-Origin", frontEndDomain);
//    response.setHeader("Access-Control-Allow-Credentials", "true");
//    response.setHeader("Access-Control-Allow-Methods", "*");
//    response.setHeader("Access-Control-Max-Age", "3600");
//    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Origin, Authorization, X-Requested-With,Access-Control-Request-Method,Access-Control-Request-Headers");
//
//	
    chain.doFilter(req, res);
}

@Override
public void init(FilterConfig filterConfig) {
}

@Override
public void destroy() {
}
}
