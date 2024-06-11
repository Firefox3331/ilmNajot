package uz.ilmnajot.school.config;

import uz.ilmnajot.school.repository.UserRepo;
import uz.ilmnajot.school.service.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Configuration
public class Filter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserRepo userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String token = request.getHeader("Authorization");
            System.out.println(token);
            if(token != null){
                String email = jwtService.extractSubject(token);
                if(!email.equals("token expired")){
                    System.out.println(email);
                    UserDetails users = userRepo.findByEmails(email).orElseThrow();
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(
                                    users.getUsername(), null, users.getAuthorities()
                            )
                    );
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }
}
