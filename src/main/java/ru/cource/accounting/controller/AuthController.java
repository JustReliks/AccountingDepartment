package ru.cource.accounting.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.cource.accounting.dto.security.JwtRequestDto;
import ru.cource.accounting.dto.security.JwtUserDto;
import ru.cource.accounting.security.JwtUserDetails;
import ru.cource.accounting.service.LoginService;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<JwtUserDto> login(@RequestBody JwtRequestDto user) throws JsonProcessingException {
        JwtUserDetails login = loginService.login(user);
        return ResponseEntity.ok(JwtUserDto.toDto(login));
    }

    @GetMapping("/current")
    public ResponseEntity<JwtUserDto> getCurrentUser() {
        try {
            JwtUserDetails current = loginService.getCurrentUser();
            return ResponseEntity.ok(JwtUserDto.toDto(current));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}
