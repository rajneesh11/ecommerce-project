package com.ekart.eKartUserAuthService.service;

import com.ekart.eKartUserAuthService.model.EkartUser;
import com.ekart.eKartUserAuthService.model.EkartUserDetails;
import com.ekart.eKartUserAuthService.repository.EkartUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EkartUserDetailsService implements UserDetailsService {
    @Autowired
    private EkartUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EkartUser ekartUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exists"));

        return EkartUserDetails.build(ekartUser);
    }
}
