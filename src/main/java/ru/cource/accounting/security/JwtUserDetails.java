package ru.cource.accounting.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.cource.accounting.models.User;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Getter
public class JwtUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;
    private String token;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public static JwtUserDetails build(User user) {
        List<GrantedAuthority> authorityList = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        JwtUserDetails jwtUserDetails = new JwtUserDetails();
        jwtUserDetails.setId(user.getId());
        jwtUserDetails.setPassword(user.getPassword());
        jwtUserDetails.setUsername(user.getUsername());
        jwtUserDetails.setAuthorities(authorityList);

        return jwtUserDetails;
    }

    public static JwtUserDetails build(User user, String token) {
        JwtUserDetails jwtUserDetails = build(user);
        jwtUserDetails.setToken(token);
        return jwtUserDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JwtUserDetails that = (JwtUserDetails) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
