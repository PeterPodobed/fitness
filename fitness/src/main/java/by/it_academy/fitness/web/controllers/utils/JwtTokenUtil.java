package by.it_academy.fitness.web.controllers.utils;

import by.it_academy.fitness.core.dto.users.UserDetailsDto;
import by.it_academy.fitness.core.dto.users.UserDto;
import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class JwtTokenUtil {

    private static final String jwtSecret = "NDQ1ZjAzNjQtMzViZi00MDRjLTljZjQtNjNjYWIyZTU5ZDYw";
    private static final String jwtIssuer = "ITAcademy";


    public static String generateAccessToken(UserDetailsDto user) {
        return generateAccessToken(new HashMap<>(), user);
    }

    public static String generateAccessToken(Map<String, Object> claims, UserDetailsDto user) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getMail())
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30))) // 30 days
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public Map<String, Object> getClaim(UserDetailsDto user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("uuid", user.getUuid());
        claims.put("fio", user.getFio());
        claims.put("date create user",user.getDt_create().toString());
        claims.put("date update user", user.getDt_update().toString());
        claims.put("role", user.getRole());
        claims.put("status", user.getStatus());
        return claims;
    }

    public static String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
//        return extractClaim(token, Claims::getSubject);
    }

//    public String getUserMail(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(jwtSecret)
//                .parseClaimsJws(token)
//                .getBody();
//
//        return claims.get("mail", String.class);
//    }

//    public static UUID getUuid(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(jwtSecret)
//                .parseClaimsJws(token)
//                .getBody();
//        return claims.get("uuid", UUID);
//    }


    private static  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    public  static String extractAuthorities(String token) {
        Function<Claims, String> claimsListFunction = claims -> (String)claims.get("role");
        return extractClaim(token, claimsListFunction);
    }

    public static Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public static boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            //logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            //logger.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            //logger.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            //logger.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            //logger.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }
}
