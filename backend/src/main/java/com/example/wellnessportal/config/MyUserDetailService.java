package com.example.wellnessportal.config;

import com.example.wellnessportal.model.AuthUser;
import com.example.wellnessportal.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AuthUser user = authUserRepository.findUserByEmail(email);
      if(user==null)
      {
       throw new UsernameNotFoundException("No user found with email: " + email);
        }

        String role = user.getRole();
        String authority = "ROLE_" + role.toUpperCase(); // e.g., ROLE_ADMIN or ROLE_EMPLOYEE

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(authority))
        );
    }
}