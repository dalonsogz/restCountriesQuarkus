package com.apitest.countries.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryResponse {
    private Map<String, Object> data;
    private boolean success;
    private String error;

    public CountryResponse(Map<String, Object> data, boolean success) {
        this.data = data;
        this.success = success;
    }
}