package br.com.infnet.academicsystemapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record StudentRequest(
        @NotBlank(message = "O nome não pode estar em branco.")
        String name,

        @NotBlank(message = "O CPF não pode estar em branco.")
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "Formato de CPF inválido. Use XXX.XXX.XXX-XX.")
        String cpf,

        @NotBlank(message = "O e-mail não pode estar me branco.")
        @Email(message = "Formato de e-mail inválido.")
        String email,

        @Size(max = 20, message = "O número de telefone não pode exceder 20 caracteres.")
        String phoneNumber,

        @Size(max = 255, message = "O endereço não pode exceder 255 caracteres.")
        String address
) {
}
