package ru.cource.accounting.dto.security;

import lombok.Data;
import ru.cource.accounting.models.User;

@Data
public class UserRegistrationDto {

    private String username;
    private String password;

    public User fromDto() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

}
