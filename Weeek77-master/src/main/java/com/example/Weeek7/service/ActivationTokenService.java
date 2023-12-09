package com.example.Weeek7.service;

import com.example.Weeek7.entity.ActivationToken;
import com.example.Weeek7.entity.User;
import com.example.Weeek7.repositories.ActivationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActivationTokenService {
    private final ActivationTokenRepository activationTokenRepository;

    public ActivationToken generateActivationToken(User user) {
        ActivationToken activationToken = new ActivationToken(user);
        return activationTokenRepository.save(activationToken);
    }

    public Optional<ActivationToken> getActivationTokenByToken(String token) {
        return activationTokenRepository.findByToken(token);
    }

    public void deleteActivationToken(ActivationToken activationToken) {
        activationTokenRepository.delete(activationToken);
    }
}
