package com.example.webshopbackend.configs;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;
@Component
public class JwtUtil {
    private static final String SECRET = "d26f7fb0e76894b0c9dfd6c887f891cc54f4aa69cdb180d31e03e4fee1da030767f7f95f0c8698060863da9befadba906c6b62e16fc61477d094650341d153ae05acd6799ae62a64852ca4d8c96eaf1e198d3b74bb8c92b5ab83063f8037a26ec756a131dfeb04f030672d20c05f809dec02d01d3f64654ff6ae918e540405b8b15c76ea21c49f370b69f1888d943efd2e341b02dee2b8155f6d15b9433b6e0b48f7d58db3461ef39bf30958ae1c2fe12d53c6498a861c6b6c19ac651cd509fa04a590c5bffd8ee8b4ad240be1b45e152ba77544548727b092143bec6ac52575ee08ffda62bbbbfed676c7cc93770b322bf8ad8c90782f4b2295c0ad85ce135c";

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));

    }
    public Optional<String> getJwtFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return Optional.empty();

        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> "jwt".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }
    public String generateToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 360 * 8))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }
    public Long extractUserId(String token) {
        return Long.parseLong(extractClaim(token, Claims::getSubject));
    }
    public boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
    public boolean validateToken(String token, Long userId) {
        return (userId.equals(extractUserId(token)) && !isTokenExpired(token));
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }
}
