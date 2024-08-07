package dev.mananhemani.markethub.Controllers;


import dev.mananhemani.markethub.DTOs.Security.LoginRequest;
import dev.mananhemani.markethub.DTOs.Security.MessageResponse;
import dev.mananhemani.markethub.DTOs.Security.SignupRequest;
import dev.mananhemani.markethub.DTOs.Security.UserInfoResponse;
import dev.mananhemani.markethub.Models.AppRole;
import dev.mananhemani.markethub.Models.Role;
import dev.mananhemani.markethub.Models.User;
import dev.mananhemani.markethub.Repositories.RoleRepository;
import dev.mananhemani.markethub.Repositories.UserRepository;
import dev.mananhemani.markethub.Security.JWT.JwtUtils;
import dev.mananhemani.markethub.Security.Services.UserDetailsImplementation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){

        Authentication authentication;
        try{
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        } catch (AuthenticationException exception){
            Map<String,Object> map = new HashMap<>();
            map.put("message","bad credentials");
            map.put("status",false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImplementation userDetails = (UserDetailsImplementation) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        UserInfoResponse response = UserInfoResponse.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .roles(roles)
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(response);
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){

        if(userRepository.existsByUserName(signupRequest.getUsername())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username already taken"));
        }

        if(userRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use"));
        }

        User user = User.builder()
                .userName(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .build();

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null){
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_BUYER)
                    .orElseThrow(() -> new RuntimeException("Error: Role not found."));
            roles.add(userRole);
        }
        else{
            for (String role : strRoles) {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                        roles.add(adminRole);
                        break;

                    case "seller":
                        Role sellerRole = roleRepository.findByRoleName(AppRole.ROLE_SELLER)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                        roles.add(sellerRole);
                        break;

                    default:
                        Role userRole = roleRepository.findByRoleName(AppRole.ROLE_BUYER)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                        roles.add(userRole);
                }

            }
        }
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok().body(new MessageResponse("User registered successfully"));
    }

    @GetMapping("/username")
    public String currentUserName(Authentication authentication){
        if(authentication!=null) return authentication.getName();
        else return "NULL";
    }

    @GetMapping("/user")
    public ResponseEntity<UserInfoResponse> getUserDetails(Authentication authentication){
        UserDetailsImplementation userDetails = (UserDetailsImplementation) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        UserInfoResponse response = UserInfoResponse.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .roles(roles)
                .build();

        return ResponseEntity.ok().body(response);
    }


    @PostMapping("/signout")
    public ResponseEntity<?> signOutUser(){
        ResponseCookie cookie = jwtUtils.getCleanCookie();
        return ResponseEntity.ok().header((HttpHeaders.SET_COOKIE),cookie.toString())
                .body("You have been logged out");
    }
}
