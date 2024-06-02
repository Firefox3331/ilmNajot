package uz.ilmnajot.school.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import uz.ilmnajot.school.entity.Users;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtProvider {

    public String generateAccessToken(Users byUsername) {
        Map<String, String> claims = new HashMap<>();
        claims.put("email", byUsername.getUsername());
        claims.put("id", byUsername.getId().toString());

        String token = Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5 ))
                .claims(claims)
                .subject(byUsername.getEmail())
                .signWith(signWithKey()).compact();
        return token;
    }

    public String extractSubject(String token){
        String email = Jwts.parser()
                .verifyWith(signWithKey())
                .build()
                .parseSignedClaims(token).getPayload().getSubject();
        return email;
    }

    public String generateRefreshToken(Users users) {
        Map<String, String> claims = new HashMap<>();
        claims.put("username", users.getUsername());
        claims.put("id", users.getId().toString());

        String token = Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .claims(claims)
                .subject(users.getId().toString())
                .signWith(signWithKey()).compact();
        return token;
    }

    private SecretKey signWithKey(){
        String secretKey = "nOSeqkcWDocgABbvlerRd7lwN2r3wvvIpsoowljwPh8=";
        byte[] decode = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decode);

    }
}
