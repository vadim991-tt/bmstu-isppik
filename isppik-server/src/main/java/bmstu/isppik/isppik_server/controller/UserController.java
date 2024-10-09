package com.example.myapp.controller;

import com.example.myapp.dto.UserDto;
import com.example.myapp.dto.user.ChangePasswordRequestDto;
import com.example.myapp.model.User;
import com.example.myapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // Регистрация нового пользователя
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        if (userService.existsByUsername(userDto.getUsername())) {
            return ResponseEntity.badRequest().body("Пользователь с таким именем уже существует");
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEnabled(true);
        userService.saveUser(user);
        return ResponseEntity.ok("Пользователь успешно зарегистрирован");
    }

    // Получение информации о текущем пользователе
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        UserDto userDto = new UserDto(user.getId(), user.getUsername());
        return ResponseEntity.ok(userDto);
    }

    // Изменение пароля
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(Principal principal, @RequestBody ChangePasswordRequestDto request) {
        User user = userService.findByUsername(principal.getName());
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Неверный текущий пароль");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userService.saveUser(user);
        return ResponseEntity.ok("Пароль успешно изменен");
    }
}
