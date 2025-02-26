package com.crio.rent_read.service;

import com.crio.rent_read.dto.UserDto;
import com.crio.rent_read.dto.UserResponseDto;
import com.crio.rent_read.entity.User;
import com.crio.rent_read.repository.UserRepository;
import com.crio.rent_read.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto registerUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        if(userDto.getRole() == null || userDto.getRole().toString() == "") {
            user.setRole(Role.CUSTOMER);
        }else {
            user.setRole(userDto.getRole());
        }

        User user1 = userRepository.save(user);

        return UserResponseDto.builder()
                .firstName(user1.getFirstName())
                .lastName(user1.getLastName())
                .id(user1.getId())
                .email(user1.getEmail())
                .role(user1.getRole())
                .build();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
}
