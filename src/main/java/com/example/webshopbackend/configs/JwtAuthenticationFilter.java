package com.example.webshopbackend.configs;

import com.example.webshopbackend.services.UserService;
import com.example.webshopbackend.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().startsWith("/api/auth");
    }


    protected void doFilterInternal(HttpServletRequest request ,HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String token = "";
        String webtoken = request.getHeader("Authorization");
        if (webtoken != null && webtoken.startsWith("Bearer ")) {
            token = webtoken.split("Bearer ")[1].trim();
        }
        else {
            Optional<String> utilCookieToken = jwtUtil.getJwtFromAuth(request);
            if (utilCookieToken.isPresent()) {
                token = utilCookieToken.get();
            }
        }

        if (!token.isBlank() && SecurityContextHolder.getContext().getAuthentication() == null) {
                Long id = jwtUtil.extractUserId(token);
                if (id != null) {
                    UserDetails user = userService.loadUserByUsername(String.valueOf(id));
                    if (jwtUtil.validateToken(token, id)) {
                        String newToken = jwtUtil.generateToken(id);
                        Cookie jwtCookie = new Cookie("jwt", newToken);
                        jwtCookie.setHttpOnly(true);
                        jwtCookie.setSecure(false);
                        jwtCookie.setPath("/");
                        jwtCookie.setMaxAge(3600 * 8);

                        response.addCookie(jwtCookie);
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            }
            filterChain.doFilter(request, response);

    }


}
