package br.com.infnet.academicsystemapi.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record GradeRequestDTO(
        @NotNull(message = "A nota é obrigatória.")
        @DecimalMin(value = "0.0", message = "A nota não pode ser menor que 0.0.")
        @DecimalMax(value = "10.0", message = "A nota não pode ser maior que 10.0.")
        BigDecimal grade
) {
}
