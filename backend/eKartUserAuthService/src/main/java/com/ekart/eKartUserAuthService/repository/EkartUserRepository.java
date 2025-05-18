package com.ekart.eKartUserAuthService.repository;

import com.ekart.eKartUserAuthService.model.EkartUser;
import com.ekart.eKartUserAuthService.model.PostalAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EkartUserRepository extends JpaRepository<EkartUser, UUID> {
    Optional<EkartUser> findByEmail(String email);
}
