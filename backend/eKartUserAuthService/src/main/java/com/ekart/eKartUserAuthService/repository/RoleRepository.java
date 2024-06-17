package com.ekart.eKartUserAuthService.repository;

import com.ekart.eKartUserAuthService.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {
    Optional<Roles> findByRole(String role);
}
