package com.hospital.controllers;

import com.hospital.dto.PasswordResetForm;
import com.hospital.dto.PasswordResetRequest;
import com.hospital.entities.Doctor;
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
import com.hospital.services.UserService;
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

  @Autowired
  UserService userService;

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
              .body(new MessageResponse("Le nom d'utilisateur est déjà pris !"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("L'adresse e-mail est déjà utilisée !"));
    }

    User user = new User(signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()));

    Set<Role> roles = new HashSet<>();
    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
            .orElseThrow(() -> new RuntimeException("Le rôle n'est pas trouvé."));
    roles.add(adminRole);
    user.setRoles(roles);
    Doctor doctor = new Doctor();
    doctor.setNom(signUpRequest.getDoctorNom());
    doctor.setPrenom(signUpRequest.getDoctorPrenom());
    doctor.setSpecialty(signUpRequest.getSpecialty());
    doctor.setPhoneNumber(signUpRequest.getPhoneNumber());
    user.setDoctor(doctor);

    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("Utilisateur enregistré avec succès !"));
  }

  @PostMapping("/request-password-reset")
  public ResponseEntity<?> requestPasswordReset(@RequestBody PasswordResetRequest request) {
    MessageResponse response = userService.sendPasswordResetEmail(request.getEmail());
    return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
            .body(response);
  }

  @PostMapping("/reset-password")
  public ResponseEntity<?> resetPassword(@RequestBody PasswordResetForm form) {
    boolean passwordReset = userService.resetPassword(form.getToken(), form.getNewPassword());
    if (passwordReset) {
      return ResponseEntity.ok(new MessageResponse("Réinitialisation du mot de passe réussie."));
    } else {
      return ResponseEntity.badRequest().body(new MessageResponse("Token invalide ou expiré."));
    }
  }



  @PostMapping("/reset-password-auth")
  public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
    Optional<User> userOptional = userRepository.findByUsername(resetPasswordRequest.getUsername());

    if (userOptional.isPresent()) {
      User user = userOptional.get();
      if (!encoder.matches(resetPasswordRequest.getOldPassword(), user.getPassword())) {
        return ResponseEntity.badRequest().body(new MessageResponse("Ancien mot de passe incorrect.", false));
      }
      user.setPassword(encoder.encode(resetPasswordRequest.getNewPassword()));
      userRepository.save(user);
      return ResponseEntity.ok(new MessageResponse("Mot de passe mis à jour avec succès.", true));
    } else {
      return ResponseEntity.badRequest().body(new MessageResponse("Utilisateur non trouvé.", false));
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUserById(@PathVariable Long id) {
    Optional<User> user = userService.findUserById(id);
    if (user.isPresent()) {
      return ResponseEntity.ok(user.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{id}/doctor")
  public ResponseEntity<?> getUserDoctorById(@PathVariable Long id) {
    Optional<Doctor> doctor = userService.findDoctorByUserId(id);
    if (doctor.isPresent()) {
      return ResponseEntity.ok(doctor.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }


  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpServletRequest request) {
    SecurityContextHolder.clearContext();
    return ResponseEntity.ok(new MessageResponse("Déconnexion réussie."));
  }
}
