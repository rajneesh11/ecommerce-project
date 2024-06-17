package com.ekart.eKartUserAuthService.service;

import com.ekart.eKartUserAuthService.dto.LoginRequest;
import com.ekart.eKartUserAuthService.dto.LoginResponse;
import com.ekart.eKartUserAuthService.dto.RegisterRequest;
import com.ekart.eKartUserAuthService.dto.RegisterResponse;
import com.ekart.eKartUserAuthService.exception.RoleNotFoundException;
import com.ekart.eKartUserAuthService.model.EkartUser;
import com.ekart.eKartUserAuthService.model.PostalAddress;
import com.ekart.eKartUserAuthService.model.Roles;
import com.ekart.eKartUserAuthService.repository.EkartUserRepository;
import com.ekart.eKartUserAuthService.repository.PostalAddressRepository;
import com.ekart.eKartUserAuthService.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private EkartUserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PostalAddressRepository addressRepository;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );
        if (!authentication.isAuthenticated())
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, HttpStatus.UNAUTHORIZED.value()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateToken((UserDetails) authentication.getPrincipal());
        return ResponseEntity
                .ok(new LoginResponse(token, HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<RegisterResponse> register(RegisterRequest request) {
        if (request == null)
            return new ResponseEntity<>(
                    new RegisterResponse("Invalid Data", null, HttpStatus.BAD_REQUEST.value()),
                    HttpStatus.BAD_REQUEST);
        EkartUser ekartUser = new EkartUser();
        ekartUser.setEmail(request.getEmail());
        ekartUser.setPassword(passwordEncoder.encode(request.getPassword()));
        ekartUser.setFullName(request.getFullName());

        Set<Roles> roleSet = request.getRoles().stream()
                .map(roles -> roleRepository.findByRole(roles.getRole())
                        .orElseThrow(() -> new RoleNotFoundException("Role " + roles.getRole() + " not found.")))
                .collect(Collectors.toSet());
        ekartUser.setRoles(roleSet);

        Set<PostalAddress> addressSet = request.getPostalAddress().stream()
                .map((postalAddress) -> addressRepository.save(postalAddress))
                .collect(Collectors.toSet());
        ekartUser.setPostalAddress(addressSet);

        return new ResponseEntity<>(
                new RegisterResponse(
                        "User registered successfully",
                        userRepository.save(ekartUser),
                        HttpStatus.CREATED.value()),
                HttpStatus.CREATED);
    }

}
