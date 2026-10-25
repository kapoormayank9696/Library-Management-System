package com.library.Library_Management_System.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class UserResponseDTO {
    // Private Access Specifier And Data Members
    private Integer userId;
    private String userName;
    private String email;
}
