package com.ekart.eKartUserAuthService.dto;

import com.ekart.eKartUserAuthService.model.EkartUser;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private String message;
    private EkartUser user;
    private int httpStatus;
}
