package com.crio.rent_read.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class LoginDto {
    @Email
    private String email;
    @Min(5)
    private String password;
}
