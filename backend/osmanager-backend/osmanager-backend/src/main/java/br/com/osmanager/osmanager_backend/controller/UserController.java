package br.com.osmanager.osmanager_backend.controller;

import br.com.osmanager.osmanager_backend.service.UserService;
import br.com.osmanager.osmanager_backend.service.dto.ConsentRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/consent")
    public ResponseEntity<Void> acceptConsent(
            @Valid @RequestBody ConsentRequest request,
            Authentication authentication) {

        userService.acceptConsent(request, authentication);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/consent")
    public ResponseEntity<Boolean> checkConsent(
            Authentication authentication) {

        boolean accepted =
                userService.hasAcceptedConsent(authentication);

        return ResponseEntity.ok(accepted);
    }
}
