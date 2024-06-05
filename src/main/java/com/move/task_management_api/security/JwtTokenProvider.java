package com.move.task_management_api.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.move.task_management_api.model.Usuario;
import com.move.task_management_api.repository.IUsuarioRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    private final UserDetailsService userDetailsService;

    private final IUsuarioRepository userRepository;

    public JwtTokenProvider(UserDetailsService userDetailsService, IUsuarioRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    public String generaToken(Usuario objUsuario) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        Map<String, Object> claims = new HashMap<>();
        claims.put("subject", objUsuario.getNombre());
        claims.put("password", objUsuario.getClave());
        claims.put("email", objUsuario.getEmail());

        return  Jwts.builder()
                    .setClaims(claims)
                    .setSubject(objUsuario.getEmail())
                    .setIssuedAt(new Date())
                    .setExpiration(expiryDate)
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();
    }

    public String getSubjectFromToken(String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(jwtSecret)
                            .parseClaimsJws(token)
                            .getBody();

        return claims.getSubject().toString();
    }

    public boolean validaToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public UserDetails loadUserById(String userId) {
        return userDetailsService.loadUserByUsername(userId);
    }

    public boolean existTokenByEmail(String email, String token){
        return userRepository.findByEmail(email)
            .map(user -> token.equals(user.getToken()))
            .orElse(false);
    }
}
