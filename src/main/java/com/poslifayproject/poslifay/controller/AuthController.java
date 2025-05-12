package com.poslifayproject.poslifay.controller;


import com.poslifayproject.poslifay.dto.LogInReq;
import com.poslifayproject.poslifay.dto.AuthResponse;
import com.poslifayproject.poslifay.dto.LogInResponse;
import com.poslifayproject.poslifay.dto.RegisterReq;
import com.poslifayproject.poslifay.model.Users;
import com.poslifayproject.poslifay.repository.UserRepository;
import com.poslifayproject.poslifay.service.JwtUtil;
import com.poslifayproject.poslifay.service.UserDetailsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil, BCryptPasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterReq registerReq) {
        if (userRepository.existsByUsername(registerReq.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(registerReq.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        Users newUser = new Users(
                registerReq.getFirstName(),
                registerReq.getLastName(),
                registerReq.getUsername(),
                registerReq.getEmail(),
                passwordEncoder.encode(registerReq.getPassword()),
                registerReq.getBirthDate(),
                registerReq.getSex(),
                registerReq.getUserImage(),
                registerReq.getAge()

        );

        userRepository.save(newUser);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(registerReq.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(jwt,registerReq.getUsername(),registerReq.getUserImage()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LogInReq logInReq) throws Exception {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(logInReq.getUsername(), logInReq.getPassword())
            );
            System.out.println("Authentication success: " + auth.isAuthenticated());
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is disabled");
        } catch (LockedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User account is locked");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }


        final UserDetails userDetails = userDetailsService.loadUserByUsername(logInReq.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new LogInResponse(jwt));
    }
}


