package com.example.makeup.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto {
    private String name;
    private String email;
    private String mobileNumber;
    private String password;
}
