package com.nano.shoppingsite.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nano.shoppingsite.repositories.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

	@Autowired
	private UserRepository repository;
	
    static final long EXPIRATIONTIME = 864_000_000; // 10 days
    static final String SECRET = "Kajs0pofj298irflskdfj";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";
    
	public void setAuthentication(Long userId,String username, String password,HttpServletResponse res) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
		authentication.isAuthenticated();
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
    	Claims userClaim = Jwts.claims();
    	userClaim.put("usr_id", userId);
    	userClaim.put("sub", username);
        String JWT = Jwts.builder()
        		.setClaims(userClaim)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
        res.setHeader("Authorization", JWT);
		return;
	}
	
	public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            String username = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody()
                    .getSubject();
            return username != null ? new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList()) : null;
        }
        return null;
    }
}
