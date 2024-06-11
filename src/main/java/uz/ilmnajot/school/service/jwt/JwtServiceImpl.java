package uz.ilmnajot.school.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.ilmnajot.school.entity.Schools;
import uz.ilmnajot.school.entity.Users;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import uz.ilmnajot.school.enums.Gender;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService{
    @Override
    public String generateAccessToken(Users user) {
        Map<String, String> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("id", user.getId().toString());

        String token = Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5 ))
                .claims(claims)
                .subject(user.getEmail())
                .signWith(signWithKey()).compact();
        return token;
    }

    @Override
    public String extractSubject(String token){
        String id="";
            try {
                DecodedJWT decodedJWT = JWT.decode(token);
                // checked that the token is not expired
                if(decodedJWT.getExpiresAt().toInstant().getEpochSecond() > Instant.now().getEpochSecond()){
                    id = Jwts.parser()
                            .verifyWith(signWithKey())
                            .build()
                            .parseSignedClaims(token).getPayload().getSubject();
                }else{
                    id = "token expired";
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        return id;
    }

    @Override
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

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    private static SecretKey signWithKeys(){
//        String secretKey = "nOSeqkcWDocgABbvlerRd7lwN2r3wvvIpsoowljwPh8=";
//        byte[] decode = Decoders.BASE64.decode(secretKey);
//        return Keys.hmacShaKeyFor(decode);
//
//    }

//    public static String generateAccessTokens(Users user) {
//        Map<String, String> claims = new HashMap<>();
//        claims.put("email", user.getEmail());
//        claims.put("id", user.getId().toString());
//
//        String token = Jwts.builder()
//                .issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 1 ))
//                .claims(claims)
//                .subject(user.getEmail())
//                .signWith(signWithKeys()).compact();
//        return token;
//    }

//    static Users user = Users.builder()
//            .id(1L)
//            .firstName("register.firstName()")
//            .lastName("register.lastName()")
//            .email("register.email()")
//            .phoneNumber("register.phoneNumber()")
//            .gender(Gender.valueOf("MALE"))
//            .school(new Schools(1L, "UZB"))
//            .password("passwordEncoder.encode(register.password())").build();

//    public static void main(String[] args) {
//        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MTc0MjkyMjMsImV4cCI6MTcxNzQyOTUyMywiaWQiOiI0IiwiZW1haWwiOiJteWRyZWFtQGdtYWlsLmNvbSIsInN1YiI6Im15ZHJlYW1AZ21haWwuY29tIn0.3XnrHq2brEh-oxfIlx57Oq3rWEApK1kQGsGUYSOTeLw";
//        String token1 = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MTc0Mzg1NjEsImV4cCI6MTcxNzQzODYyMSwiaWQiOiIxIiwiZW1haWwiOiJyZWdpc3Rlci5lbWFpbCgpIiwic3ViIjoicmVnaXN0ZXIuZW1haWwoKSJ9.xfhxNxIC9zrjY1w_w4iIp3IsLffv5e85kM_RpLM8w4Q";
//        System.out.println(token1);
//        try {
//            DecodedJWT decodedJWT = JWT.decode(token);
//            long epochSecond = decodedJWT.getExpiresAt().toInstant().getEpochSecond();
//            DecodedJWT decodedJWTs = JWT.decode(token1);
//            long epochSeconds = decodedJWTs.getExpiresAt().toInstant().getEpochSecond();
//            long currentTime = Instant.now().getEpochSecond();
//            System.out.println(epochSecond < currentTime ? "token eskirgan" : "eskirmagan");
//            System.out.println(epochSeconds < currentTime ? "token eskirgan" : "eskirmagan");
//
//        } catch (JWTDecodeException e) {
//            // ...
//        }
//    }
}
