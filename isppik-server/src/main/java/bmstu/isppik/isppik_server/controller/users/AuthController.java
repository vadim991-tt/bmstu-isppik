package bmstu.isppik.isppik_server.controller.users;

import bmstu.isppik.isppik_server.dto.users.LoginRequestDto;
import bmstu.isppik.isppik_server.dto.users.LoginResponseDto;
import bmstu.isppik.isppik_server.dto.users.RegisterRequestDto;
import bmstu.isppik.isppik_server.model.users.User;
import bmstu.isppik.isppik_server.service.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * Регистрация пользователя
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDto request) {
        final User newUser = userService.registerUser(
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                request.getPhone()
        );
        return ResponseEntity.ok("User registered successfully: " + newUser.getUsername());
    }

    /**
     * Аутентификация пользователя (логин)
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDto request) {
        final LoginResponseDto response = userService.loginUser(
                request.getUsername(),
                request.getPassword()
        );
        return ResponseEntity.ok(response);
    }
}