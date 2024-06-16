package com.hospital.controllers;

import com.hospital.entities.ERole;
import com.hospital.entities.Role;
import com.hospital.entities.User;
import com.hospital.payload.request.LoginRequest;
import com.hospital.payload.request.ResetPasswordRequest;
import com.hospital.payload.request.SignupRequest;
import com.hospital.payload.response.JwtResponse;
import com.hospital.payload.response.MessageResponse;
import com.hospital.repositories.RoleRepository;
import com.hospital.repositories.UserRepository;
import com.hospital.security.jwt.JwtUtils;
import com.hospital.security.services.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    try {
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

      SecurityContextHolder.getContext().setAuthentication(authentication);

      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      if (userDetails.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
        String jwt = jwtUtils.generateJwtToken(authentication);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
        ));
      } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Le rôle du compte doit être administrateur."));
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Nom d'utilisateur ou mot de passe invalide."));
    }
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Erreur : le nom d'utilisateur est déjà pris !"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Erreur : l'adresse e-mail est déjà utilisée !"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()));

    Set<Role> roles = new HashSet<>();
    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
            .orElseThrow(() -> new RuntimeException("Erreur : le rôle n'est pas trouvé."));
    roles.add(adminRole);

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("Utilisateur enregistré avec succès !"));
  }

  @PostMapping("/reset-password")
  public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
    Optional<User> userOptional = userRepository.findByUsername(resetPasswordRequest.getUsername());

    if (userOptional.isPresent()) {
      User user = userOptional.get();
      user.setPassword(encoder.encode(resetPasswordRequest.getNewPassword()));
      userRepository.save(user);
      return ResponseEntity.ok(new MessageResponse("Mot de passe mis à jour avec succès."));
    } else {
      return ResponseEntity.badRequest().body(new MessageResponse("Erreur : Utilisateur non trouvé."));
    }
  }


  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpServletRequest request) {
    SecurityContextHolder.clearContext();
    return ResponseEntity.ok(new MessageResponse("Déconnexion réussie."));
  }
}
