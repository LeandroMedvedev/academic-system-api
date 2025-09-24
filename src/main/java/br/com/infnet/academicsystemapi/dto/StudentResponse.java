package br.com.infnet.academicsystemapi.dto;

public record StudentResponse(
        Long id,
        String name,
        String cpf,
        String email
) {
}
