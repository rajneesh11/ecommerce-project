package com.ekart.eKartUserAuthService.dto;

import com.ekart.eKartUserAuthService.model.PostalAddress;
import com.ekart.eKartUserAuthService.model.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;


@Data
@AllArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private Set<PostalAddress> postalAddress;
    private String fullName;
    private Set<Roles> roles;
}
