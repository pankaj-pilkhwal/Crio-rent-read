package com.crio.rent_read.controller;

import com.crio.rent_read.dto.LoginDto;
import com.crio.rent_read.dto.UserDto;
import com.crio.rent_read.dto.UserResponseDto;
import com.crio.rent_read.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;



    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.registerUser(userDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginDto loginDto) {
        System.out.println("login page hit");
        return new ResponseEntity<>("Login successful", HttpStatus.OK);
    }

}
