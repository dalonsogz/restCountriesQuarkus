package com.apitest.countries.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserDto {
    private String fullName;
    private String email;
    private String password;
}