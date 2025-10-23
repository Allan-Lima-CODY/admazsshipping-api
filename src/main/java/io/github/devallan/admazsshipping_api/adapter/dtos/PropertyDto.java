package io.github.devallan.admazsshipping_api.adapter.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PropertyDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PropertyRequestDto {
        private Long id;

        @NotNull(message = "Customer ID is required")
        private Long customerId;

        @NotBlank(message = "Name is required")
        private String name;

        @NotBlank(message = "Type is required")
        private String type;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PropertyResponseDto {
        private Long id;
        private Long customerId;
        private String name;
        private String type;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PropertyUpdateDto {
        @NotBlank(message = "Name is required")
        private String name;
    }
}
