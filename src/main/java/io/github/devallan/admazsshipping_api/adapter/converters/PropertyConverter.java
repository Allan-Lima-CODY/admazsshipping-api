package io.github.devallan.admazsshipping_api.adapter.converters;

import io.github.devallan.admazsshipping_api.adapter.dtos.PropertyDto;
import io.github.devallan.admazsshipping_api.core.domain.Property;
import io.github.devallan.admazsshipping_api.core.ports.CustomerServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PropertyConverter {

    private final CustomerServicePort customerServicePort;

    public Property toDomain(PropertyDto.PropertyRequestDto dto) {
        return new Property(
                dto.getId(),
                customerServicePort.findCustomerById(dto.getCustomerId()),
                dto.getName(),
                dto.getType()
        );
    }

    public PropertyDto.PropertyResponseDto toResponseDto(Property property) {
        return new PropertyDto.PropertyResponseDto(
                property.getId(),
                property.getCustomer().getId(),
                property.getName(),
                property.getType()
        );
    }

    public Property toUpdateDomain(Long id, PropertyDto.PropertyUpdateDto dto) {
        return new Property(
                id,
                null,
                dto.getName(),
                null
        );
    }
}
