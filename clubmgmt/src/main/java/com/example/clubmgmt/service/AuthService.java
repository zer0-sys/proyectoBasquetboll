package com.example.clubmgmt.service;

import com.example.clubmgmt.dto.LoginRequest;
import com.example.clubmgmt.dto.RegisterRequest;
import com.example.clubmgmt.dto.UserDTO;
import com.example.clubmgmt.entity.User;
import com.example.clubmgmt.repository.UserRepository;
import com.example.clubmgmt.util.EntityDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private EntityDtoConverter converter;

    public UserDTO register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El correo electrónico ya está registrado");
        }
        
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER"); // Rol por defecto
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        
        return converter.convertToDto(userRepository.save(user));
    }

    public Optional<UserDTO> login(LoginRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .map(converter::convertToDto);
    }
    
    public Optional<User> getUserEntityByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}