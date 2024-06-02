package uz.ilmnajot.school.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.ilmnajot.school.security.jwt.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    private final AuthenticationProvider authenticationProvider;
    public SecurityConfig(JwtFilter jwtFilter, AuthenticationProvider authenticationProvider) {
        this.jwtFilter = jwtFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
//                .cors(Customizer.withDefaults())
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/singUp/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/users/deleteUser/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/users/removeRoleToUser/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/users/updateUser/**").hasAnyRole("ADMIN","USER")
                        .requestMatchers(HttpMethod.GET, "/users/getUser/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/users/getUsers/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(
                                "/v2/api-docs/**",
                                "/v3/api-docs/**",
                                "/swagg^er-resources/**",
                                "/configuration/ui/**",
                                "configuration/security/**",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "swagger-ui.html/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}

//                .formLogin(login -> {
//                    login.loginPage("/api/auth/login");
//                    login.defaultSuccessUrl("/");
//                    login.failureUrl("/login-error");
//                })
//                .logout(logout -> {
//                    logout.logoutRequestMatcher(new AntPathRequestMatcher("logout"));
//                    logout.logoutSuccessUrl("/");
//                    logout.deleteCookies("JSESSIONID");
//                    logout.invalidateHttpSession(true);
//                });






