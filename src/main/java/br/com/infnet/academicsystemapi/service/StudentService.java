package br.com.infnet.academicsystemapi.service;

import br.com.infnet.academicsystemapi.dto.StudentRequestDTO;
import br.com.infnet.academicsystemapi.dto.StudentResponseDTO;
import br.com.infnet.academicsystemapi.exception.BusinessException;
import br.com.infnet.academicsystemapi.exception.ResourceNotFoundException;
import br.com.infnet.academicsystemapi.model.Student;
import br.com.infnet.academicsystemapi.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    @Transactional
    public StudentResponseDTO createStudent(StudentRequestDTO requestDTO) {
        studentRepository.findByCpf(requestDTO.cpf()).ifPresent(s -> {
            throw new BusinessException("CPF já cadastrado: " + requestDTO.cpf());
        });
        studentRepository.findByEmail(requestDTO.email()).ifPresent(s -> {
            throw new BusinessException("E-mail já cadastrado: " + requestDTO.email());
        });

        Student student = new Student();
        mapDtoToEntity(requestDTO, student);
        Student savedStudent = studentRepository.save(student);

        return new StudentResponseDTO(savedStudent);
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(StudentResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentById(Long id) {
        Student student = findStudentById(id);
        return new StudentResponseDTO(student);
    }

    public Student findStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com o ID: " + id));
    }

    private void mapDtoToEntity(StudentRequestDTO dto, Student student) {
        student.setName(dto.name());
        student.setCpf(dto.cpf());
        student.setEmail(dto.email());
        student.setPhoneNumber(dto.phoneNumber());
        student.setAddress(dto.address());
    }
}
