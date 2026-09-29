package br.com.osmanager.osmanager_backend.service;

import br.com.osmanager.osmanager_backend.entity.User;
import br.com.osmanager.osmanager_backend.exception.ResourceNotFoundException;
import br.com.osmanager.osmanager_backend.repository.UserRepository;
import br.com.osmanager.osmanager_backend.service.dto.ConsentRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository ;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void acceptConsent(
            ConsentRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuário autenticado não encontrado"
                        ));

        user.setTermsAccepted(request.getTermsAccepted());
        user.setPrivacyPolicyAccepted(
                request.getPrivacyPolicyAccepted()
        );
        user.setConsentAcceptedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    public boolean hasAcceptedConsent(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuário autenticado não encontrado"
                        ));

        return Boolean.TRUE.equals(user.getTermsAccepted())
                && Boolean.TRUE.equals(user.getPrivacyPolicyAccepted());
    }
}
