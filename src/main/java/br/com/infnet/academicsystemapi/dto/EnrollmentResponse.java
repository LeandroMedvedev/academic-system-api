package br.com.infnet.academicsystemapi.dto;

import java.math.BigDecimal;

public record EnrollmentResponse(
        Long enrollmentId,
        Long studentId,
        String studentName,
        Long courseId,
        String courseName,
        BigDecimal grade
) {
}
