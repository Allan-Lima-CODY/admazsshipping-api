package io.github.devallan.admazsshipping_api.adapter.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CustomerDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerRequestDto {
        private Long id;

        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 100, message = "Name must have between 2 and 100 characters")
        private String name;

        @NotBlank(message = "Email is required")
        @Email(message = "Email format is invalid")
        private String email;

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerResponseDto {
        private Long id;
        private String name;
        private String email;
    }
}
