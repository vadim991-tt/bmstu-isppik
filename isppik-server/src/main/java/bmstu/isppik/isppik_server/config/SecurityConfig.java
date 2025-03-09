package bmstu.isppik.isppik_server.config;

import bmstu.isppik.isppik_server.model.users.User;
import bmstu.isppik.isppik_server.service.users.JwtProviderService;
import bmstu.isppik.isppik_server.service.users.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(JwtProviderService jwtProvider, UserService userService, HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .anyRequest().authenticated()
                ).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtAuthFilter(jwtProvider, userService),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @AllArgsConstructor
    private static class JwtAuthFilter extends OncePerRequestFilter {

        private final JwtProviderService jwtProvider;

        private final UserService userService;


        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain filterChain)
                throws ServletException, IOException {

            final String authHeader = request.getHeader("Authorization");
            if (!isBearerAuth(authHeader)) {
                filterChain.doFilter(request, response);
                return;
            }


            final String token = authHeader.substring(7);
            if (!isTokenValid(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            final String username = jwtProvider.getUsernameFromToken(token);
            final User user = userService.findByUsername(username).orElse(null);
            if (user == null) {
                filterChain.doFilter(request, response);
                return;
            }


            final List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

            final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                user.getId(), null, authorities
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        }

        private boolean isBearerAuth(String authHeader) {
            return StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ");
        }

        private boolean isTokenValid(String token) {
            return jwtProvider.validateToken(token);
        }

    }


}

