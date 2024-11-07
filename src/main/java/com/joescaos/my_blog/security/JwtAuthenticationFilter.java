package com.joescaos.my_blog.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;

  private final UserDetailsService userDetailsService;

  public JwtAuthenticationFilter(
      JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    // get token
    String token = getTokenFromRequest(request);

    // validate token
    if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {

      // get username from token
      String username = jwtTokenProvider.getUserName(token);

      // load user associated with token
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);

      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
    filterChain.doFilter(request, response);
  }

  // get JWT from request
  private String getTokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");

    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
