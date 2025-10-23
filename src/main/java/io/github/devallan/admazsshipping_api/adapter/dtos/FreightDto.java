package io.github.devallan.admazsshipping_api.adapter.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class FreightDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FreightRequestDto {
        private Long id;

        @NotNull(message = "Name is required")
        private String name;

        @NotNull(message = "Customer ID is required")
        private Long customerId;

        @NotNull(message = "Created date is required")
        private LocalDateTime createdAt;

        @Valid
        private List<ValuePropertyFreightRequestDto> properties;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FreightResponseDto {
        private Long id;
        private Long customerId;
        private LocalDateTime createdAt;
        private List<ValuePropertyFreightResponseDto> properties;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FreightNameUpdateDto {
        @NotBlank(message = "Freight name is required")
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FreightBasicResponseDto {
        private Long id;
        private Long customerId;
        private String name;
        private LocalDateTime createdAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ValuePropertyFreightRequestDto {
        @NotNull(message = "Property ID is required")
        private Long propertyId;

        @NotBlank(message = "Value is required")
        private String value;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ValuePropertyFreightResponseDto {
        private Long id;
        private Long propertyId;
        private String propertyName;
        private String type;
        private String value;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ValuePropertyFreightUpdateDto {
        @NotNull(message = "Property ID is required")
        private Long propertyId;

        @NotBlank(message = "Value is required")
        private String value;
    }
}
