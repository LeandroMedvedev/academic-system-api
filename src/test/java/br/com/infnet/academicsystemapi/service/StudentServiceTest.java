package br.com.infnet.academicsystemapi.service;

import br.com.infnet.academicsystemapi.dto.StudentRequestDTO;
import br.com.infnet.academicsystemapi.dto.StudentResponseDTO;
import br.com.infnet.academicsystemapi.exception.BusinessException;
import br.com.infnet.academicsystemapi.exception.ResourceNotFoundException;
import br.com.infnet.academicsystemapi.model.Student;
import br.com.infnet.academicsystemapi.repository.StudentRepository;
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
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private StudentRequestDTO dougHeffernanRequest;
    private Student dougHeffernanEntity;

    @BeforeEach
    void setUp() {
        dougHeffernanRequest = new StudentRequestDTO(
                "Doug Heffernan", "111.111.111-11", "doug@ips.com", "555-1234", "Queens, NY"
        );
        dougHeffernanEntity = new Student();
        dougHeffernanEntity.setId(1L);
        dougHeffernanEntity.setName(dougHeffernanRequest.name());
        dougHeffernanEntity.setCpf(dougHeffernanRequest.cpf());
        dougHeffernanEntity.setEmail(dougHeffernanRequest.email());
    }

    @Test
    @DisplayName("Deve criar um estudante com sucesso quando os dados são válidos")
    void createStudent_WithValidData_ShouldSucceed() {

        when(studentRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(studentRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(studentRepository.save(any(Student.class))).thenReturn(dougHeffernanEntity);

        StudentResponseDTO response = studentService.createStudent(dougHeffernanRequest);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("Doug Heffernan");

        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    @DisplayName("Deve lançar BusinessException ao tentar criar estudante com CPF duplicado")
    void createStudent_WithDuplicateCpf_ShouldThrowBusinessException() {

        when(studentRepository.findByCpf(dougHeffernanRequest.cpf())).thenReturn(Optional.of(dougHeffernanEntity));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            studentService.createStudent(dougHeffernanRequest);
        });

        assertThat(exception.getMessage()).isEqualTo("CPF já cadastrado: " + dougHeffernanRequest.cpf());

        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao buscar um estudante por ID inexistente")
    void findStudentById_WithNonExistentId_ShouldThrowResourceNotFoundException() {
        long nonExistentId = 99L;
        when(studentRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            studentService.findStudentById(nonExistentId);
        });
    }
}