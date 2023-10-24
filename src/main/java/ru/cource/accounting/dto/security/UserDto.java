package ru.cource.accounting.dto.security;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.cource.accounting.models.Role;
import ru.cource.accounting.models.User;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
@EqualsAndHashCode
public class UserDto {

    private Long id;
    private String username;
    private Set<String> roles;

    public static UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));

        return dto;
    }

    public User fromDto() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        return user;
    }

}
