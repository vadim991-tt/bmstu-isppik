package bmstu.isppik.isppik_server;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "admin"; // Ваш пароль
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println(encodedPassword);
    }
}
