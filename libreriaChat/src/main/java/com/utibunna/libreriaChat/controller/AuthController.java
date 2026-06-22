package com.utibunna.libreriaChat.controller;

import com.utibunna.libreriaChat.security.JwtService;
import com.utibunna.libreriaChat.service.CustomUserDetailService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService userDetailService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request, HttpServletResponse response){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())

        );

        UserDetails userDetails = userDetailService.loadUserByUsername(request.email());
        String  jwtToken = jwtService.generarToken(userDetails);

        Cookie jwtCookie = new Cookie("jtw", jwtToken);

        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(3600);

        response.addCookie(jwtCookie);

        return ResponseEntity.ok().body("Autenticación exitosa. Cookie configurada");


    }

}
