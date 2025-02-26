package com.crio.rent_read.dto;


import com.crio.rent_read.util.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDto {

    @Email
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    private Role role;
}
