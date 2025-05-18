package com.ekart.eKartUserAuthService.service;

import com.ekart.eKartUserAuthService.model.PostalAddress;
import com.ekart.eKartUserAuthService.repository.PostalAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private PostalAddressRepository repository;

    @Override
    public ResponseEntity<?> getPreferredAddress() {
//        Optional<PostalAddress> preferredAddress = repository.getPreferredAddress();
//        if (preferredAddress.isEmpty())
//            return ResponseEntity.badRequest().build();
//        return ResponseEntity.ok(preferredAddress.get());
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<?> setPreferredAddress(UUID addressId) {
//        repository.updateAllByPreferredAddress(false);
//        repository.updatePreferredAddressById(addressId, true);
        return ResponseEntity.ok().build();
    }
}
