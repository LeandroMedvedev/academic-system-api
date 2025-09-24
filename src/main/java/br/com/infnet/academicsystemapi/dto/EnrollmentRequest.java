package br.com.infnet.academicsystemapi.dto;

import jakarta.validation.constraints.NotNull;

public record EnrollmentRequest(
        @NotNull(message = "O ID do aluno é obrigatório.")
        Long studentId,

        @NotNull(message = "O ID da disciplina é obrigatório.")
        Long courseId
) {
}
