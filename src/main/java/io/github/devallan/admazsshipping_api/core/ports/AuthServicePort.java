package io.github.devallan.admazsshipping_api.core.ports;

import io.github.devallan.admazsshipping_api.adapter.dtos.LoginDto;

    public interface AuthServicePort {
        LoginDto.LoginResponseDto login(LoginDto.LoginRequestDto request);
    }
