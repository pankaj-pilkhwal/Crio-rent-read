package com.crio.rent_read.service;

import com.crio.rent_read.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        com.crio.rent_read.entity.User user = userRepository
                .findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("user with email id: " + email));


        return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(String.valueOf(user.getRole()))
                .build();
    }


}
