package com.joescaos.my_blog.security;

import com.joescaos.my_blog.exceptions.BlogAPIException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  @Value("${app.jwt.secret}")
  private String jwtSecret;

  @Value("${app.jwt.expiration-milliseconds}")
  private long jwtExpirationDate;


  // generate jwt token
  public String generateToken(Authentication authentication) {
    String username = authentication.getName();

    Date currentDate = new Date();

    Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

    return Jwts.builder()
        .subject(username)
        .issuedAt(new Date())
        .expiration(expireDate)
        .signWith(key())
        .compact();
  }

  // convert to key base on secret
  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  // get username from token
  public String getUserName(String token) {
    return Jwts.parser()
        .verifyWith((SecretKey) key())
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
  }

  // validate jwt token
  public boolean validateToken(String token) {
    try {
      Jwts.parser().verifyWith((SecretKey) key()).build().parse(token);
      return true;
    } catch (MalformedJwtException e) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
    } catch (ExpiredJwtException e) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
    } catch (UnsupportedJwtException e) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
    } catch (IllegalArgumentException e) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Jwt claims string is null or empty");
    }
  }
}
