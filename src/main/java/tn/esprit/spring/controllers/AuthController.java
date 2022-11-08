package tn.esprit.spring.controllers;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.dao.entities.ERole;
import tn.esprit.spring.dao.entities.Role;
import tn.esprit.spring.dao.entities.User;
import tn.esprit.spring.security.jwt.JwtUtils;
import tn.esprit.spring.security.request.LoginRequest;
import tn.esprit.spring.security.request.ResetPasswordRequest;
import tn.esprit.spring.security.request.SignupRequest;
import tn.esprit.spring.security.response.JwtResponse;
import tn.esprit.spring.security.response.MessageResponse;
import tn.esprit.spring.services.impls.UserDetailsImpl;
import tn.esprit.spring.services.inters.IUserService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  IUserService userService;


  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();  
 
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());
    

    return ResponseEntity.ok(new JwtResponse(jwt, 
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         userDetails.getEmail(), 
                         roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws UnsupportedEncodingException, MessagingException {
    if (userService.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userService.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(), 
               signUpRequest.getEmail(),
               encoder.encode(signUpRequest.getPassword()));
    
    
    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role clientRole = userService.findByName(ERole.ROLE_EMPLOYEE)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(clientRole);
      user.setRole("EMPLOYEE");

      
    } else {
      strRoles.forEach(role -> {
        switch (role) {
     
        case "admin":
          Role adminRole = userService.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);
          user.setRole("ADMIN");


          break;
     
        default:
          Role clientRole = userService.findByName(ERole.ROLE_EMPLOYEE)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(clientRole);
          user.setRole("EMPLOYEE");

        }
      });
    }

    user.setRoles(roles);
    userService.saveUser(user);
    
    String response= user.getVerificationCode();
	response = "http://localhost:8081/LevelUp/api/auth/verify?code=" + response;
	userService.sendEmail(user.getEmail(), response);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
  
  @GetMapping("/verify")
	public String verifyUser(@RequestParam String code) {
	    return userService.verify(code);
	    
	}
  @PostMapping("/forgot-password")
  @ResponseBody

	public String forgotPassword(@RequestBody String email) throws UnsupportedEncodingException, MessagingException {
		String response = userService.forgotPassword(email);

		if (!response.startsWith("Invalid")) {
			response = "http://localhost:4200/reset-password?token=" + response;
		}
		userService.sendEmail(email, response);
		return response;
	}
  
  @PutMapping("/reset-password")
  @ResponseBody

	public String resetPassword(@RequestBody  ResetPasswordRequest resetPaswordRequest ) {

	  	System.out.println(resetPaswordRequest.getPassword()) ;
		return userService.resetPassword(resetPaswordRequest.getToken() , resetPaswordRequest.getPassword());
	}
}

