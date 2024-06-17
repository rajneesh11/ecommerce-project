package com.ekart.eKartUserAuthService.repository;

import com.ekart.eKartUserAuthService.model.PostalAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostalAddressRepository extends JpaRepository<PostalAddress, UUID> {
    Optional<PostalAddress> findByPinCode(String pinCode);
}
