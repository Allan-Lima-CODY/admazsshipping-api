package io.github.devallan.admazsshipping_api.adapter.repositories;

import io.github.devallan.admazsshipping_api.adapter.entities.CustomerEntity;
import io.github.devallan.admazsshipping_api.adapter.entities.PropertyEntity;
import io.github.devallan.admazsshipping_api.core.domain.Property;
import io.github.devallan.admazsshipping_api.core.ports.PropertyRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class PropertyRepositoryAdapter implements PropertyRepositoryPort {
    private final PropertyRepository propertyRepository;
    private final ModelMapper modelMapper;

    @Override
    public Property createProperty(Property property) {
        CustomerEntity customerEntity = modelMapper.map(property.getCustomer(), CustomerEntity.class);
        PropertyEntity propertyEntity = modelMapper.map(property, PropertyEntity.class);

        propertyEntity.setCustomerEntity(customerEntity);

        PropertyEntity newProperty = propertyRepository.save(propertyEntity);

        return modelMapper.map(newProperty, Property.class);
    }

    @Override
    public Property findByName(String name) {
        PropertyEntity entity = propertyRepository.findByName(name);
        return (entity != null) ? modelMapper.map(entity, Property.class) : null;
    }

    @Override
    public Collection<Property> findAllByCustomer(Long customerId) {
        return propertyRepository.findAllByCustomerEntity_Id(customerId).stream().map(propertyEntity -> modelMapper.map(propertyEntity, Property.class)).toList();
    }

    @Override
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }

    @Override
    public Property findById(Long id) {
        PropertyEntity entity = propertyRepository.findById(id).orElse(null);
        return (entity != null) ? modelMapper.map(entity, Property.class) : null;
    }

    @Override
    public Property updateProperty(Property property) {
        PropertyEntity entity = propertyRepository.findById(property.getId()).orElse(null);

        if (entity == null) return null;

        entity.setName(property.getName());
        PropertyEntity updated = propertyRepository.save(entity);

        return modelMapper.map(updated, Property.class);
    }
}
