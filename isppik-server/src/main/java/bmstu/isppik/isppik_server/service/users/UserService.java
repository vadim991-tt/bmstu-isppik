package bmstu.isppik.isppik_server.service.users;

import bmstu.isppik.isppik_server.dto.users.LoginResponseDto;
import bmstu.isppik.isppik_server.model.users.Role;
import bmstu.isppik.isppik_server.model.users.User;
import bmstu.isppik.isppik_server.model.users.enums.RoleType;
import bmstu.isppik.isppik_server.repository.users.RoleRepository;
import bmstu.isppik.isppik_server.repository.users.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProviderService jwtProvider;



    @Transactional
    public User registerUser(String username, String password, String email, String phone) {
        if (userExists(username)) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }

        // Хэшируем пароль перед сохранением
        String hashedPassword = passwordEncoder.encode(password);

        // Загружаем роль USER
        Role userRole = roleRepository.findByName(RoleType.ROLE_USER);

        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(hashedPassword);
        user.setEmail(email);
        user.setPhone(phone);
        user.setRoles(List.of(userRole));

        return userRepository.save(user);
    }


    @Transactional
    public LoginResponseDto loginUser(String username, String password) {
        User user = findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("Неверное имя пользователя или пароль")
        );

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new IllegalArgumentException("Неверное имя пользователя или пароль");
        }

        String token = jwtProvider.generateToken(user.getUsername());

        return new LoginResponseDto(token, user.getUsername());
    }


    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }
}
