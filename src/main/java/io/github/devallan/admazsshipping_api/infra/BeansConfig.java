package io.github.devallan.admazsshipping_api.infra;

import io.github.devallan.admazsshipping_api.core.ports.*;
import io.github.devallan.admazsshipping_api.core.services.CustomerService;
import io.github.devallan.admazsshipping_api.core.services.FreightService;
import io.github.devallan.admazsshipping_api.core.services.PropertyService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CustomerServicePort customerServiceImpl(CustomerRepositoryPort customerRepositoryPort) {
        return new CustomerService(customerRepositoryPort);
    }

    @Bean
    public FreightServicePort freightServiceImpl(FreightRepositoryPort freightRepositoryPort,
                                                 PropertyRepositoryPort  propertyRepositoryPort,
                                                 ValuePropertyFreightRepositoryPort valuePropertyFreightRepositoryPort) {
        return new FreightService(freightRepositoryPort, propertyRepositoryPort,  valuePropertyFreightRepositoryPort);
    }

    @Bean
    public PropertyServicePort propertyServiceImpl(PropertyRepositoryPort propertyRepositoryPort,
                                                   ValuePropertyFreightRepositoryPort valuePropertyFreightRepositoryPort) {
        return new PropertyService(propertyRepositoryPort, valuePropertyFreightRepositoryPort);
    }
}
