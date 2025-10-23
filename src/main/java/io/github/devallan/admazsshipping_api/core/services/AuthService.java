package io.github.devallan.admazsshipping_api.core.services;

import io.github.devallan.admazsshipping_api.adapter.dtos.LoginDto;
import io.github.devallan.admazsshipping_api.core.domain.Customer;
import io.github.devallan.admazsshipping_api.core.ports.AuthServicePort;
import io.github.devallan.admazsshipping_api.core.ports.CustomerRepositoryPort;
import io.github.devallan.admazsshipping_api.infra.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthService implements AuthServicePort {
    private final CustomerRepositoryPort customerRepositoryPort;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public LoginDto.LoginResponseDto login(LoginDto.LoginRequestDto request) {
        Customer customer = customerRepositoryPort.findByEmail(request.getEmail());

        if (customer == null || !passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            throw new IllegalArgumentException("Email or password is incorrect!");
        }

        String token = jwtUtil.generateToken(customer.getId());

        return new LoginDto.LoginResponseDto(token);
    }
}
