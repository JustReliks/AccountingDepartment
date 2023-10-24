package ru.cource.accounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cource.accounting.auditing.ApplicationAuditAware;
import ru.cource.accounting.models.Role;
import ru.cource.accounting.models.User;
import ru.cource.accounting.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class TestRolesController {

    private final ApplicationAuditAware aware;
    private final UserRepository userRepository;

    @GetMapping("/api/test/viewer")
    public ResponseEntity<List<String>> testViewer() {
        User user = userRepository.findById(aware.getCurrentAuditor().orElseThrow()).orElseThrow();
        return ResponseEntity.ok(List.of(user.getUsername(), user.getRoles().stream().map(Role::getName).collect(Collectors.joining())));
    }

    @GetMapping("/api/test/editor")
    public ResponseEntity<List<String>> testEditor() {
        User user = userRepository.findById(aware.getCurrentAuditor().orElseThrow()).orElseThrow();
        return ResponseEntity.ok(List.of(user.getUsername(), user.getRoles().stream().map(Role::getName).collect(Collectors.joining())));
    }

    @GetMapping("/api/test/any")
    public ResponseEntity<List<String>> testAny() {
        User user = userRepository.findById(aware.getCurrentAuditor().orElseThrow()).orElseThrow();
        return ResponseEntity.ok(List.of(user.getUsername(), user.getRoles().stream().map(Role::getName).collect(Collectors.joining())));
    }


}
