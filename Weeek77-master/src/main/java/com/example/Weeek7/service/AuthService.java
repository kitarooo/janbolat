package com.example.Weeek7.service;

import com.example.Weeek7.dto.JwtRequest;
import com.example.Weeek7.dto.JwtResponse;
import com.example.Weeek7.dto.RegistrationUserDto;
import com.example.Weeek7.dto.UserDto;
import com.example.Weeek7.entity.ActivationToken;
import com.example.Weeek7.entity.User;
import com.example.Weeek7.exception.AppError;
import com.example.Weeek7.repositories.UserRepository;
import com.example.Weeek7.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final ActivationTokenService activationTokenService;
    private final EmailService emailService;
    private final UserRepository userRepository;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());

        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if (userRepository.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с таким именем уже существует"), HttpStatus.BAD_REQUEST);
        }
        long countByEmail = userRepository.countByEmail(registrationUserDto.getEmail());
        if (countByEmail > 0) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с таким email уже существует"), HttpStatus.BAD_REQUEST);
        }

        User user = userService.createNewUser(registrationUserDto);
        ActivationToken activationToken = activationTokenService.generateActivationToken(user);
        sendActivationEmail(user.getEmail(), activationToken.getToken());

        return ResponseEntity.ok(new UserDto(user.getId(), user.getUsername(), user.getEmail()));
    }


    public void activateUserByToken(String token) {
        Optional<ActivationToken> tokenOptional = activationTokenService.getActivationTokenByToken(token);
        if (tokenOptional.isPresent()) {
            ActivationToken activationToken = tokenOptional.get();
            User user = activationToken.getUser();
            user.setEnabled(true);
            userRepository.save(user);
            activationTokenService.deleteActivationToken(activationToken);
        }else throw new IllegalArgumentException("Activation token not found");
    }

    public void sendActivationEmail(String email, String token) {
        String activationLink = "http://68.183.64.48:8081/auth/activate?token=" + token;
        emailService.sendEmail(email, activationLink);
    }


}
