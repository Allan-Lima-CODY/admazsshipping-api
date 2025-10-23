package io.github.devallan.admazsshipping_api.adapter.controllers;

import io.github.devallan.admazsshipping_api.adapter.converters.FreightConverter;
import io.github.devallan.admazsshipping_api.adapter.dtos.FreightDto;
import io.github.devallan.admazsshipping_api.core.domain.Freight;
import io.github.devallan.admazsshipping_api.core.domain.ValuePropertyFreight;
import io.github.devallan.admazsshipping_api.core.ports.FreightServicePort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/freight")
@RequiredArgsConstructor
public class FreightController {
    private final FreightServicePort freightServicePort;
    private final FreightConverter freightConverter;

    @GetMapping("/customer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public List<FreightDto.FreightBasicResponseDto> getFreightsByCustomer(@PathVariable Long customerId) {
        List<Freight> freights = freightServicePort.findAllByCustomer(customerId);
        return freights.stream()
                .map(freightConverter::toBasicResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{freightId}/values")
    public List<FreightDto.ValuePropertyFreightResponseDto> getFreightValues(
            @PathVariable Long freightId,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<ValuePropertyFreight> values = freightServicePort.getFreightValues(freightId, search, page, size);

        return values.stream()
                .map(freightConverter::toValuePropertyFreightResponseDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public FreightDto.FreightResponseDto createFreight(@Valid @RequestBody FreightDto.FreightRequestDto dto) {
        Freight freight = freightServicePort.createFreight(freightConverter.toDomain(dto));
        return freightConverter.toResponseDto(freight);
    }

    @DeleteMapping("/{freightId}/values")
    public void deleteFreightValues(@PathVariable Long freightId, @RequestBody List<Long> propertyIds) {

        freightServicePort.deleteFreightValues(freightId, propertyIds);
    }

    @PutMapping("/{freightId}/values")
    public ResponseEntity<Void> updateFreightValues(@PathVariable Long freightId, @Valid @RequestBody List<FreightDto.ValuePropertyFreightUpdateDto> valueDtos) {
        List<ValuePropertyFreight> values = valueDtos.stream()
                .map(freightConverter::toDomainUpdate)
                .collect(Collectors.toList());

        freightServicePort.updateFreightValues(freightId, values);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{freightId}/name")
    public ResponseEntity<Void> updateFreightName(@PathVariable Long freightId, @Valid @RequestBody FreightDto.FreightNameUpdateDto dto) {
        Freight freight = freightConverter.toDomainNameUpdate(dto, freightId);
        freightServicePort.updateFreightName(freight);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{freightId}")
    public ResponseEntity<Void> deleteFreight(@PathVariable Long freightId) {
        freightServicePort.deleteFreightAndValues(freightId);
        return ResponseEntity.noContent().build();
    }
}