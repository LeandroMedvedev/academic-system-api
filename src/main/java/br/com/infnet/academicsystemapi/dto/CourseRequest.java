package br.com.infnet.academicsystemapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CourseRequest(
        @NotBlank(message = "O nome da disciplina não pode estar em branco.")
        String name,

        @NotBlank(message = "O código da disciplina não pode estar em branco.")
        @Size(min = 3, max = 10, message = "O código deve ter entre 3 e 10 caracteres.")
        String code
) {
}
