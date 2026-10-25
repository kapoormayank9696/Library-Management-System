package com.library.Library_Management_System.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class UserRequestDTO {
    @NotBlank(message = "User name must be required")
    @Size (
            max = 255,
            message = "User name cannot be exceed from 255 characters"
    )
    private String userName;

    @NotBlank(message = "Email is must be required")
    @Size (
            max = 255,
            message = "Email cannot be exceed from 255 characters"
    )
    private String email;
}
