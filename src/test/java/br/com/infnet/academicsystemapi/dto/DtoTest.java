package br.com.infnet.academicsystemapi.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

class DtoTest {

    @Test
    @DisplayName("Deve criar e validar as propriedades dos DTOs")
    void testDtoCreationAndAccessors() {
        var authRequest = new AuthenticationRequestDTO("user", "pass");
        assertThat(authRequest.username()).isEqualTo("user");
        assertThat(authRequest.password()).isEqualTo("pass");

        var courseRequest = new CourseRequestDTO("Science", "SCI101");
        assertThat(courseRequest.name()).isEqualTo("Science");
        assertThat(courseRequest.code()).isEqualTo("SCI101");

        var studentRequest = new StudentRequestDTO("Student Name", "123.456.789-00", "student@test.com", "555-0101", "123 Main St");
        assertThat(studentRequest.cpf()).isEqualTo("123.456.789-00");

        var enrollmentRequest = new EnrollmentRequestDTO(1L, 2L);
        assertThat(enrollmentRequest.studentId()).isEqualTo(1L);

        var gradeRequest = new GradeRequestDTO(new BigDecimal("9.5"));
        assertThat(gradeRequest.grade()).isEqualTo(new BigDecimal("9.5"));

        var tokenResponse = new TokenResponseDTO("jwt-token");
        assertThat(tokenResponse.token()).isEqualTo("jwt-token");
    }
}
