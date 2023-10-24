package ru.cource.accounting.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.cource.accounting.dto.security.JwtRequestDto;
import ru.cource.accounting.security.JwtUserDetails;
import ru.cource.accounting.service.LoginService;
import ru.cource.accounting.service.UserService;
import ru.cource.accounting.service.security.UserDetailsServiceImpl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserService userService;
    private final UserDetailsServiceImpl jwtUserDetailsService;

    @Override
    public JwtUserDetails login(JwtRequestDto user) {
        String password = new String(Base64.getDecoder().decode(user.getPassword()), StandardCharsets.UTF_8);
        return userService.authenticate(user.getUsername(), password);
    }

    @Override
    public JwtUserDetails getCurrentUser() {
        return jwtUserDetailsService.getCurrentUser();
    }
}
