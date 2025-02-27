package com.corpseed.gateway.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {


    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    private String jwtSecret="======================corpseed=Spring===========================";

    
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
      }

      public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                   .parseClaimsJws(token).getBody().getSubject();
      }

      public boolean validateToken(String authToken) {
        try {
          Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
          return true;
        } catch (MalformedJwtException e) {
//          logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
//          logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
//          logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
//          logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
      }
}
