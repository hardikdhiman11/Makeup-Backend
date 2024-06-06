package com.example.makeup.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.security.Keys;
import jakarta.websocket.Decoder;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

import java.security.Key;
import java.util.Base64;

public class JwtUtils {
    @Value("{jwt.secret}")
    private String jwtSercret;

    private Key signingKey(){
        byte [] keyBytes = Base64.getDecoder().decode(jwtSercret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(){
         return Jwts.parserBuilder().setSigningKey(signingKey()).build().parseClaimsJws(jwtSercret).getBody();
    }

    public String getUserNameFromJwt(String jwtSercret){

    }

}
