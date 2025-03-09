package bmstu.isppik.isppik_server.service.users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class JwtProviderService {
    private static final String SECRET_KEY_EXAMPLE = "SECRET_KEY_EXAMPLE"; // FIXME: Секретный ключ (меняем в проде!)

    private static final long JWT_EXPIRATION_MS = 3600000; // Время жизни токена (1 час)

    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(username) // Кто владелец токена (username)
                .setIssuedAt(now) // Дата создания
                .setExpiration(expiryDate) // Дата истечения
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY_EXAMPLE) // Подписываем
                .compact();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY_EXAMPLE).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("Ошибка валидации токена: " + e.getMessage());
        }
        return false;
    }


    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY_EXAMPLE)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // В setSubject() мы сохраняли username
    }
}
