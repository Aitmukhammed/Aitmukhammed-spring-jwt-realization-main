package com.example.demoauth.configs.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demoauth.service.UserDetailsServiceImpl;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			String jwt = parseJwt(request);
			log.info("Extracted JWT: {}", jwt); // добавьте логирование здесь

			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				log.info("Valid JWT token"); // добавьте логирование здесь
				String username = jwtUtils.getUserNameFromJwtToken(jwt);
				log.info("Username from JWT: {}", username); // добавьте логирование здесь

				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);

				log.info("User authenticated: " + username); // Логируем успешную аутентификацию
			} else {
				log.info("Invalid JWT token"); // Логируем невалидный JWT
			}
		} catch (Exception e) {
			log.error("Cannot set user authentication: {}", e);
		}

		filterChain.doFilter(request, response);
	}

//	private String parseJwt(HttpServletRequest request) {
//		String headerAuth = request.getHeader("Authorization");
//		log.info("Authorization Header: {}", headerAuth); // добавьте логирование здесь
//
//		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
//			return headerAuth.substring(7);
//		}
//
//		return null;
//	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		log.info("Authorization Header: {}", headerAuth); // Логирование заголовка

		if (headerAuth != null) {
			// Логирование префикса, если он есть
			if (headerAuth.startsWith("Bearer ")) {
				log.info("Token has 'Bearer ' prefix");
				return headerAuth.substring(7);
			} else {
				log.info("Token does not have 'Bearer ' prefix");
				return headerAuth;
			}
		}

		return null;
	}

}

