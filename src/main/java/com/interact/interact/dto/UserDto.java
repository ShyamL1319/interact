package com.interact.interact.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long userId;

    @NotEmpty
    @Size(min = 4, message = "Username must be min of 4 character")
    private String name;

    @Email(message = "Email address is not Valid")
    private String email;

    @NotEmpty
    @Size(min = 3, max = 16, message = "Mininmum 3 and max 16 chars are allowed!")
    private String password;

    @NotEmpty
    private String about;
}
