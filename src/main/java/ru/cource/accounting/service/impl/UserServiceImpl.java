package ru.cource.accounting.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cource.accounting.models.User;
import ru.cource.accounting.repository.RoleRepository;
import ru.cource.accounting.repository.UserRepository;
import ru.cource.accounting.security.JwtUserDetails;
import ru.cource.accounting.security.jwt.JwtUtils;
import ru.cource.accounting.service.UserService;
import ru.cource.accounting.utils.RoleAuthorities;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    @Transactional
    public Long save(User user) {
        user.setRoles(Collections.singleton(roleRepository.findByName(RoleAuthorities.EDITOR.name()).orElseThrow()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user).getId();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public JwtUserDetails authenticate(String username, String password) {
        String jwt;
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,
                password));
        SecurityContextHolder.getContext().setAuthentication(auth);
        jwt = jwtUtils.generateJwtToken(auth);
        ((JwtUserDetails) auth.getPrincipal()).setToken(jwt);

        return (JwtUserDetails) auth.getPrincipal();
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id: " + id +
                " " +
                "not found!"));
    }

    @Override
    public User getUser(String userName) {
        return userRepository.findByUsername(userName).orElseThrow(() ->
                new EntityNotFoundException("Пользователь " + userName + " не найден!"));
    }

}
