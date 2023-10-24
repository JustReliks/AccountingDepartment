package ru.cource.accounting.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cource.accounting.dto.security.UserRegistrationDto;
import ru.cource.accounting.models.User;
import ru.cource.accounting.service.RegistrationService;

@Slf4j
@RestController
@RequestMapping("/api/registration")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody UserRegistrationDto dto) {
        if (registrationService.checkUserExistsByUsername(dto.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User user = dto.fromDto();

        try {
            Long userId = registrationService.register(user);
            return ResponseEntity.ok().body("New user created successfully with id: " + userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error when creating user: " + e.getMessage());
        }
    }

    public ResponseEntity<Boolean> checkExists(@RequestParam(value = "username") String username) {
        return ResponseEntity.ok(registrationService.checkUserExistsByUsername(username));
    }

}
