package ru.cource.accounting.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cource.accounting.models.User;
import ru.cource.accounting.repository.UserRepository;
import ru.cource.accounting.service.RegistrationService;
import ru.cource.accounting.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserService userService;
    private final UserRepository repository;

    @Transactional
    @Override
    public Long register(User user) {
        String password = new String(Base64.getDecoder().decode(user.getPassword()), StandardCharsets.UTF_8);
        user.setPassword(password);
            return userService.save(user);
    }

    @Override
    public boolean checkUserExistsByUsername(String username) {
        return repository.findByUsername(username).isPresent();
    }
}
