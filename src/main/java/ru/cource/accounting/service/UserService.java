package ru.cource.accounting.service;

import ru.cource.accounting.models.User;
import ru.cource.accounting.security.JwtUserDetails;

import java.util.Optional;

public interface UserService {

    Long save(User user);

    Optional<User> findByUsername(String username);

    JwtUserDetails authenticate(String username, String password);

    User getUser(Long id);

    User getUser(String userName);
}
