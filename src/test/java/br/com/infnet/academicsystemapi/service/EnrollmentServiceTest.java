package br.com.infnet.academicsystemapi.service;

import br.com.infnet.academicsystemapi.dto.EnrollmentRequestDTO;
import br.com.infnet.academicsystemapi.dto.GradeRequestDTO;
import br.com.infnet.academicsystemapi.dto.StudentResponseDTO;
import br.com.infnet.academicsystemapi.exception.BusinessException;
import br.com.infnet.academicsystemapi.exception.ResourceNotFoundException;
import br.com.infnet.academicsystemapi.model.Course;
import br.com.infnet.academicsystemapi.model.Enrollment;
import br.com.infnet.academicsystemapi.model.Student;
import br.com.infnet.academicsystemapi.repository.EnrollmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;
    @Mock
    private StudentService studentService;
    @Mock
    private CourseService courseService;

    @InjectMocks
    private EnrollmentService enrollmentService;

    private Student carrieHeffernan;
    private Course lawClass;
    private Enrollment enrollment;

    @BeforeEach
    void setUp() {
        carrieHeffernan = new Student();
        carrieHeffernan.setId(2L);
        carrieHeffernan.setName("Carrie Heffernan");
        carrieHeffernan.setCpf("222.222.222-22");
        carrieHeffernan.setEmail("carrie@lawfirm.com");

        lawClass = new Course();
        lawClass.setId(2L);
        lawClass.setName("Contract Law");
        lawClass.setCode("LAW202");

        enrollment = new Enrollment();
        enrollment.setId(1L);
        enrollment.setStudent(carrieHeffernan);
        enrollment.setCourse(lawClass);
    }

    @Test
    @DisplayName("Deve matricular um aluno em uma disciplina com sucesso")
    void enrollStudent_WithValidData_ShouldSucceed() {
        when(studentService.findStudentById(2L)).thenReturn(carrieHeffernan);
        when(courseService.findCourseById(2L)).thenReturn(lawClass);
        when(enrollmentRepository.findByStudentIdAndCourseId(2L, 2L)).thenReturn(Optional.empty());

        EnrollmentRequestDTO request = new EnrollmentRequestDTO(2L, 2L);
        enrollmentService.enrollStudent(request);

        verify(enrollmentRepository, times(1)).save(any(Enrollment.class));
    }

    @Test
    @DisplayName("Deve lançar BusinessException ao tentar matricular aluno já matriculado")
    void enrollStudent_WhenAlreadyEnrolled_ShouldThrowBusinessException() {
        when(studentService.findStudentById(2L)).thenReturn(carrieHeffernan);
        when(courseService.findCourseById(2L)).thenReturn(lawClass);
        when(enrollmentRepository.findByStudentIdAndCourseId(2L, 2L)).thenReturn(Optional.of(enrollment));

        EnrollmentRequestDTO request = new EnrollmentRequestDTO(2L, 2L);

        assertThrows(BusinessException.class, () -> {
            enrollmentService.enrollStudent(request);
        });

        verify(enrollmentRepository, never()).save(any(Enrollment.class));
    }

    @Test
    @DisplayName("Deve atribuir uma nota a uma matrícula existente com sucesso")
    void assignGrade_WithValidEnrollment_ShouldSucceed() {
        when(enrollmentRepository.findByStudentIdAndCourseId(2L, 2L)).thenReturn(Optional.of(enrollment));

        GradeRequestDTO gradeRequest = new GradeRequestDTO(new BigDecimal("8.5"));
        enrollmentService.assignGrade(2L, 2L, gradeRequest);

        verify(enrollmentRepository, times(1)).save(enrollment);
        assertThat(enrollment.getGrade()).isEqualTo(new BigDecimal("8.5"));
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao tentar atribuir nota a matrícula inexistente")
    void assignGrade_WithNonExistentEnrollment_ShouldThrowResourceNotFoundException() {
        when(enrollmentRepository.findByStudentIdAndCourseId(2L, 2L)).thenReturn(Optional.empty());

        GradeRequestDTO gradeRequest = new GradeRequestDTO(new BigDecimal("9.0"));

        assertThrows(ResourceNotFoundException.class, () -> {
            enrollmentService.assignGrade(2L, 2L, gradeRequest);
        });
    }

    @Test
    @DisplayName("Deve retornar a lista de alunos aprovados para uma disciplina")
    void getApprovedStudentsByCourse_ShouldReturnListOfApprovedStudents() {
        Student arthurSpooner = new Student();
        arthurSpooner.setId(3L);
        arthurSpooner.setName("Arthur Spooner");

        when(courseService.findCourseById(2L)).thenReturn(lawClass);
        when(enrollmentRepository.findApprovedStudentsByCourse(2L)).thenReturn(List.of(carrieHeffernan, arthurSpooner));

        List<StudentResponseDTO> approvedStudents = enrollmentService.getApprovedStudentsByCourse(2L);

        assertThat(approvedStudents).isNotNull();
        assertThat(approvedStudents).hasSize(2);
        assertThat(approvedStudents.get(0).name()).isEqualTo("Carrie Heffernan");
    }
}
