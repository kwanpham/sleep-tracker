package quandev.com.sleeptracker.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import quandev.com.sleeptracker.service.SecurityUserService;

import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {


    private static final  String SECRET_KEY = "SLEEPTRACKER";


    private static final long EXPIRE_TIME = 1200000;

    public String getBase64EncodedSecretKey(){
        return Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    public String generateToken(SecurityUserService.CustomUserDetails customUserDetails) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + EXPIRE_TIME);

        return Jwts.builder()
                .setSubject((customUserDetails.getUserEntity().getUsername()))
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, getBase64EncodedSecretKey())
                .compact();

    }

    public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getBase64EncodedSecretKey())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(getBase64EncodedSecretKey()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT Signature");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty");
        }
        return false;
    }
}
