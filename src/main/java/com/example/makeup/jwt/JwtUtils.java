package com.example.makeup.jwt;

import com.example.makeup.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtUtils {
    @Value("{jwt.secret}")
    private  String jwtSercret;


    public Key key(){
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSercret));
    }
    public <T> T extractClaims(String token,Function<Claims, T> getClaims) {
        var claims = Jwts
                .parserBuilder()
                .setSigningKey(key()).build()
                .parseClaimsJws(token).getBody();
        return getClaims.apply(claims);
    }
    public String getUserNameFromJwt(String jwtSecret){
        var email = extractClaims(jwtSecret,Claims::getSubject);
        return email;
    }

    public String generateJwt(Authentication authentication){
        var userPrincipal = (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(null)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateJwt(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true;
        }catch (MalformedJwtException e) {
            log.error("Invalid JWT Token : {}",e.getMessage());
        }catch(ExpiredJwtException e){
            log.error("JWT Token is Expired : {}",e. getMessage());
        }catch(UnsupportedJwtException e){
            log.error("Unsupported JWT :{}", e.getMessage());
        }catch(IllegalArgumentException e){
            log.error("JWT Payload is Empty: {}", e.getMessage());
        }
        return false;
    }


}
