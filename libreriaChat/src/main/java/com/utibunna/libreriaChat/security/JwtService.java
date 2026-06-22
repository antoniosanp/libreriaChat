package com.utibunna.libreriaChat.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {

    // Secreto estático para el laboratorio (En producción, póngalo en application.properties)
    private static final String SECRET = "EstaEsUnaClaveSuperSecretaParaLibroTechQueDebeMedirMasDe256Bits";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 horas

    public  String generarToken(UserDetails userDetails){
        HashMap<String, Object> claims = new HashMap<>();
        String rol = userDetails.getAuthorities().iterator().next().getAuthority();
        claims.put("rol" , rol);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME ))
                .signWith(getSignInKey(), SignatureAlgorithm.ES256)
                .compact();
    }

    public  String extraerUsername(String token){
        return extrearTodosLosClaims(token).getSubject();
    }
    public  boolean esTokenValido(String token, UserDetails userDetails){
        final String username = extraerUsername(token);
        return (username.equals(userDetails.getUsername()) && !esTokenExpirado(token));
    }

    private Key getSignInKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    private Claims extrearTodosLosClaims(String token){
        return Jwts.parserBuilder().
                setSigningKey(getSignInKey()).build()
                .parseClaimsJws(token).getBody();
    }

    private  boolean esTokenExpirado(String token){
        return extrearTodosLosClaims(token).getExpiration().before(new Date());
    }





}
