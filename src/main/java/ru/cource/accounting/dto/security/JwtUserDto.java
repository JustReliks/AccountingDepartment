package ru.cource.accounting.dto.security;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import ru.cource.accounting.models.Role;
import ru.cource.accounting.models.User;
import ru.cource.accounting.security.JwtUserDetails;

import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class JwtUserDto extends UserDto {
    private String token;

    public static JwtUserDto toDto(User user, String token) {
        JwtUserDto dto = new JwtUserDto();
        dto.setToken(token);
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));

        return dto;
    }

    public static JwtUserDto toDto(JwtUserDetails details) {
        JwtUserDto dto = new JwtUserDto();
        dto.setToken(details.getToken());
        dto.setId(details.getId());
        dto.setUsername(details.getUsername());
        dto.setRoles(details.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));

        return dto;
    }
}
