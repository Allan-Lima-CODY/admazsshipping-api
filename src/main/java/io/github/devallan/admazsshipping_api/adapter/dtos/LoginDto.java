package io.github.devallan.admazsshipping_api.adapter.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class LoginDto {
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class LoginRequestDto {
        private String email;
        private String password;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class LoginResponseDto {
        private String token;
    }
}
