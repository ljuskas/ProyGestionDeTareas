package com.move.task_management_api.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${app.token.prefijo}")
    private String tokenPrefijo;

    @Value("${app.token.header}")
    private String tokenHeader;

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(tokenHeader);
        if (header == null ) {
            if(request.getRequestURI().contains("private")){
                handleException(request, response, "Token no encontrado");
                return;
            }
            filterChain.doFilter(request, response); 
            return;
        }
        try {
            String token = getJwtFromRequest(request);

            if (token != null && jwtTokenProvider.validaToken(token)) {
                String userId = jwtTokenProvider.getSubjectFromToken(token);
                if(jwtTokenProvider.existTokenByEmail(userId, token)){
                    UserDetails userDetails = jwtTokenProvider.loadUserById(userId);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                                                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }else{
                    handleException(request, response, "Token no registra para email "+ userId);
                    return;
                }
            }else{
                handleException(request, response, "Token invalido");
                return;
            }
            filterChain.doFilter(request, response);
        }catch (Exception e) {
            handleException(request, response, e.getMessage());
            return;
        }
        
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, String message) throws IOException, ServletException {
        String errorPage = "/api/public/auth/error";
        String requestMethod = request.getMethod();
        request.getSession().setAttribute("error", message);

        if ("DELETE".equalsIgnoreCase(requestMethod)||"POST".equalsIgnoreCase(requestMethod)||"PUT".equalsIgnoreCase(requestMethod)) {
            response.sendRedirect(errorPage);
        } else {
            if (!response.isCommitted()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher(errorPage);
                if (dispatcher != null) {
                    dispatcher.forward(request, response);
                } else {
                    System.err.println("Error: RequestDispatcher es nulo para la página de error: " + errorPage);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } else {
                System.err.println("Error: La respuesta ya ha sido enviada.");
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }
    

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(tokenHeader);
        if (bearerToken != null && bearerToken.startsWith(tokenPrefijo)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
