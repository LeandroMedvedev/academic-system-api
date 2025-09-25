package br.com.infnet.academicsystemapi.service;

import br.com.infnet.academicsystemapi.dto.CourseRequestDTO;
import br.com.infnet.academicsystemapi.dto.CourseResponseDTO;
import br.com.infnet.academicsystemapi.exception.BusinessException;
import br.com.infnet.academicsystemapi.exception.ResourceNotFoundException;
import br.com.infnet.academicsystemapi.model.Course;
import br.com.infnet.academicsystemapi.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    private CourseRequestDTO historyClassRequest;
    private Course historyClassEntity;

    @BeforeEach
    void setUp() {
        historyClassRequest = new CourseRequestDTO("American History", "HIST101");

        historyClassEntity = new Course();
        historyClassEntity.setId(1L);
        historyClassEntity.setName(historyClassRequest.name());
        historyClassEntity.setCode(historyClassRequest.code());
    }

    @Test
    @DisplayName("Deve criar uma disciplina com sucesso quando os dados são válidos")
    void createCourse_WithValidData_ShouldSucceed() {
        when(courseRepository.findByCode(anyString())).thenReturn(Optional.empty());
        when(courseRepository.save(any(Course.class))).thenReturn(historyClassEntity);

        CourseResponseDTO response = courseService.createCourse(historyClassRequest);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("American History");
        assertThat(response.code()).isEqualTo("HIST101");

        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    @DisplayName("Deve lançar BusinessException ao tentar criar disciplina com código duplicado")
    void createCourse_WithDuplicateCode_ShouldThrowBusinessException() {
        when(courseRepository.findByCode(historyClassRequest.code())).thenReturn(Optional.of(historyClassEntity));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            courseService.createCourse(historyClassRequest);
        });

        assertThat(exception.getMessage()).isEqualTo("Código de disciplina já cadastrado: " + historyClassRequest.code());
        verify(courseRepository, never()).save(any(Course.class));
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao buscar uma disciplina por ID inexistente")
    void findCourseById_WithNonExistentId_ShouldThrowResourceNotFoundException() {
        long nonExistentId = 99L;
        when(courseRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            courseService.findCourseById(nonExistentId);
        });
    }

    @Test
    @DisplayName("Deve retornar uma disciplina quando o ID existir")
    void findCourseById_WithExistingId_ShouldReturnCourse() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(historyClassEntity));

        Course foundCourse = courseService.findCourseById(1L);

        assertThat(foundCourse).isNotNull();
        assertThat(foundCourse.getId()).isEqualTo(1L);
    }
}
