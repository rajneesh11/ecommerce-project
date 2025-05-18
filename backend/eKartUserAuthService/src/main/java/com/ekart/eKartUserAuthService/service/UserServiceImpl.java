package com.ekart.eKartUserAuthService.service;

import com.ekart.eKartUserAuthService.model.EkartUser;
import com.ekart.eKartUserAuthService.model.PostalAddress;
import com.ekart.eKartUserAuthService.repository.EkartUserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private EkartUserRepository userRepository;

    @Override
    public ResponseEntity<?> getPreferredAddress(String email) {
        /*Optional<EkartUser> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty())
            return ResponseEntity.badRequest().body("User does not exist.");
        PostalAddress address = userOptional.get();
        ObjectMapper mapper = new ObjectMapper();
        try {
            String preferredAddress = mapper.writeValueAsString(address);
            return ResponseEntity.ok(preferredAddress);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }*/
        return null;
    }
}
