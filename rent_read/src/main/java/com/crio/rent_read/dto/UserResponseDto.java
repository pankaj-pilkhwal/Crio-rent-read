package com.crio.rent_read.dto;

import com.crio.rent_read.util.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {

    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private Role role;
}
