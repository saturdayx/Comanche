package com.java.companion;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public class JwtUtils {

    private static final String key = "HelloWorld";//签名私钥(key值太短会报错)
    private static final Long ttl = 60 * 1000L * 30;//30分钟


    public static Claims parseToken(String token){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token.replace("Bearer ","")).getBody();
    }

    public static String createToken(Authentication authentication) {
        UserDetails principal = (UserDetails)authentication.getPrincipal();
        StringBuffer authorities = new StringBuffer();
        for (GrantedAuthority authority : principal.getAuthorities()) {
            authorities.append(authority.getAuthority()).append(",");
        }
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, key)
                .setExpiration(new Date(System.currentTimeMillis() + ttl))
                .claim("authorities", authorities).compact();
    }
}
