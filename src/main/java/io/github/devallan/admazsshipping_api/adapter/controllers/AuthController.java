package io.github.devallan.admazsshipping_api.adapter.controllers;

import io.github.devallan.admazsshipping_api.adapter.dtos.LoginDto;
import io.github.devallan.admazsshipping_api.core.ports.AuthServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServicePort authServicePort;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginDto.LoginResponseDto login(@RequestBody LoginDto.LoginRequestDto loginRequestDto) {
        return authServicePort.login(loginRequestDto);
    }
}
