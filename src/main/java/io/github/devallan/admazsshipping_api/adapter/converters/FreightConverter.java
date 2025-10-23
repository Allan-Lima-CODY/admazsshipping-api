package io.github.devallan.admazsshipping_api.adapter.converters;

import io.github.devallan.admazsshipping_api.adapter.dtos.FreightDto;
import io.github.devallan.admazsshipping_api.core.domain.Freight;
import io.github.devallan.admazsshipping_api.core.domain.ValuePropertyFreight;
import io.github.devallan.admazsshipping_api.core.ports.CustomerServicePort;
import io.github.devallan.admazsshipping_api.core.ports.PropertyServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FreightConverter {
    private final CustomerServicePort customerServicePort;
    private final PropertyServicePort propertyServicePort;

    public Freight toDomain(FreightDto.FreightRequestDto dto) {
        var customer = customerServicePort.findCustomerById(dto.getCustomerId());

        List<ValuePropertyFreight> properties = (dto.getProperties() != null)
                ? dto.getProperties().stream()
                .map(this::toValuePropertyFreight)
                .collect(Collectors.toList())
                : List.of();

        return new Freight(dto.getId(), dto.getName(), customer, dto.getCreatedAt(), properties);
    }

    private ValuePropertyFreight toValuePropertyFreight(FreightDto.ValuePropertyFreightRequestDto dto) {
        var property = propertyServicePort.findById(dto.getPropertyId());
        ValuePropertyFreight value = new ValuePropertyFreight();
        value.setProperty(property);
        value.setValue(dto.getValue());
        return value;
    }

    public FreightDto.FreightResponseDto toResponseDto(Freight freight) {
        List<FreightDto.ValuePropertyFreightResponseDto> propertyDtos = freight.getValues().stream()
                .map(this::toValuePropertyFreightResponseDto)
                .collect(Collectors.toList());

        return new FreightDto.FreightResponseDto(
                freight.getId(),
                freight.getCustomer().getId(),
                freight.getCreatedAt(),
                propertyDtos
        );
    }

    public FreightDto.ValuePropertyFreightResponseDto toValuePropertyFreightResponseDto(ValuePropertyFreight value) {
        return new FreightDto.ValuePropertyFreightResponseDto(
                value.getId(),
                value.getProperty().getId(),
                value.getProperty().getName(),
                value.getProperty().getType(),
                value.getValue()
        );
    }

    public ValuePropertyFreight toDomainUpdate(FreightDto.ValuePropertyFreightUpdateDto dto) {
        var property = propertyServicePort.findById(dto.getPropertyId());
        ValuePropertyFreight value = new ValuePropertyFreight();
        value.setProperty(property);
        value.setValue(dto.getValue());
        return value;
    }

    public Freight toDomainNameUpdate(FreightDto.FreightNameUpdateDto dto, Long id) {
        Freight freight = new Freight();
        freight.setId(id);
        freight.setName(dto.getName());
        return freight;
    }

    public FreightDto.FreightBasicResponseDto toBasicResponseDto(Freight freight) {
        return new FreightDto.FreightBasicResponseDto(
                freight.getId(),
                freight.getCustomer().getId(),
                freight.getName(),
                freight.getCreatedAt()
        );
    }
}
