package br.com.infnet.academicsystemapi.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequestDTO(
        @NotBlank
        String username,

        @NotBlank
        String password
) {
}
