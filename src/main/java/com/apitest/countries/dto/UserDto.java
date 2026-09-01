package com.apitest.countries.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Integer id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String website;
}