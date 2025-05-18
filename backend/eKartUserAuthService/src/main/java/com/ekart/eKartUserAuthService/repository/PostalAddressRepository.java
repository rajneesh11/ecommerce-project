package com.ekart.eKartUserAuthService.repository;

import com.ekart.eKartUserAuthService.model.PostalAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostalAddressRepository extends JpaRepository<PostalAddress, UUID> {
    Optional<PostalAddress> findByPinCode(String pinCode);

//    PostalAddress findByPreferredAddress(boolean preferredAddress);

//    void updateAllByPreferredAddress(boolean preferredAddress);

//    void updateByAddressId(UUID addressId, boolean preferredAddress);

//    @Modifying
//    @Transactional
//    @Query("UPDATE PostalAddress p SET p.preferredAddress = :preferred WHERE p.addressId = :addressId")
//    int updatePreferredAddressById(@Param("addressId") UUID addressId, @Param("preferred") boolean preferred);

//    @Query("SELECT p FROM PostalAddress p WHERE p.preferredAddress = true")
//    Optional<PostalAddress> getPreferredAddress();
}
