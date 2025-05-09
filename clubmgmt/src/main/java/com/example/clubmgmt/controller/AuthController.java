package com.example.clubmgmt.controller;

import com.example.clubmgmt.dto.JwtResponse;
import com.example.clubmgmt.dto.LoginRequest;
import com.example.clubmgmt.dto.RegisterRequest;
import com.example.clubmgmt.dto.UserDTO;
import com.example.clubmgmt.security.JwtUtil;
import com.example.clubmgmt.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(request.getEmail());
        
        Optional<UserDTO> userOpt = authService.login(request);
        if (userOpt.isPresent()) {
            UserDTO user = userOpt.get();
            return ResponseEntity.ok(new JwtResponse(jwt, user.getId(), user.getEmail(), user.getRole()));
        }
        
        return ResponseEntity.status(401).body("Credenciales inválidas");
    }
}