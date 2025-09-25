package br.com.infnet.academicsystemapi.dto;

import java.math.BigDecimal;

public record EnrollmentResponseDTO(
        Long enrollmentId,
        Long studentId,
        String studentName,
        Long courseId,
        String courseName,
        BigDecimal grade
) {
}
