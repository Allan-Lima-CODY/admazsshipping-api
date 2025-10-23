package io.github.devallan.admazsshipping_api.adapter.controllers;

import io.github.devallan.admazsshipping_api.adapter.converters.PropertyConverter;
import io.github.devallan.admazsshipping_api.adapter.dtos.PropertyDto;
import io.github.devallan.admazsshipping_api.core.domain.Property;
import io.github.devallan.admazsshipping_api.core.ports.PropertyServicePort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/property")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyServicePort propertyServicePort;
    private final PropertyConverter propertyConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PropertyDto.PropertyResponseDto createProperty(@Valid @RequestBody PropertyDto.PropertyRequestDto dto) {
        Property property = propertyServicePort.createProperty(propertyConverter.toDomain(dto));

        return propertyConverter.toResponseDto(property);
    }

    @GetMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public List<PropertyDto.PropertyResponseDto> findAllByCustomer(@PathVariable Long customerId) {
        return propertyServicePort.findAllByCustomer(customerId)
                .stream()
                .map(propertyConverter::toResponseDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PropertyDto.PropertyResponseDto updateProperty(@PathVariable Long id, @Valid @RequestBody PropertyDto.PropertyUpdateDto dto) {
        Property updated = propertyServicePort.updateProperty(propertyConverter.toUpdateDomain(id, dto));

        return propertyConverter.toResponseDto(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProperty(@PathVariable Long id) {
        propertyServicePort.deleteProperty(id);
    }
}
