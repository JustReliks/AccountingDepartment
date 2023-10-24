package ru.cource.accounting.service;

import ru.cource.accounting.models.User;

public interface RegistrationService {

    Long register(User user);

    boolean checkUserExistsByUsername(String username);

}
