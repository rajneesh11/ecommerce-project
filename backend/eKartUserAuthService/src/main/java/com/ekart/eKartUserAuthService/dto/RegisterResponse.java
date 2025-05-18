package com.ekart.eKartUserAuthService.dto;

import com.ekart.eKartUserAuthService.model.EkartUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private String message;
    @JsonIgnore
    private EkartUser user;
    private int httpStatus;
}
