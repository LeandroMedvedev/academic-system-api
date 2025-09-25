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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentService studentService;
    private final CourseService courseService;

    @Transactional
    public void enrollStudent(EnrollmentRequestDTO requestDTO) {
        Student student = studentService.findStudentById(requestDTO.studentId());
        Course course = courseService.findCourseById(requestDTO.courseId());

        enrollmentRepository.findByStudentIdAndCourseId(student.getId(), course.getId()).ifPresent(e -> {
            throw new BusinessException("Aluno já matriculado nesta disciplina.");
        });

        Enrollment enrollment = new Enrollment();
        mapDtoToEntity(enrollment, student, course);

        enrollmentRepository.save(enrollment);
    }

    @Transactional
    public void assignGrade(Long studentId, Long courseId, GradeRequestDTO requestDTO) {
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Matrícula não encontrada para o aluno " + studentId + " na disciplina " + courseId)
                );

        enrollment.setGrade(requestDTO.grade());
        enrollmentRepository.save(enrollment);
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getApprovedStudentsByCourse(Long courseId) {
        courseService.findCourseById(courseId);
        List<Student> students = enrollmentRepository.findApprovedStudentsByCourse(courseId);

        return students.stream()
                .map(StudentResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getDisapprovedStudentsByCourse(Long courseId) {
        courseService.findCourseById(courseId);
        List<Student> students = enrollmentRepository.findDisapprovedStudentsByCourse(courseId);

        return students.stream()
                .map(StudentResponseDTO::new)
                .collect(Collectors.toList());
    }

    private void mapDtoToEntity(Enrollment enrollment, Student student, Course course) {
        enrollment.setStudent(student);
        enrollment.setCourse(course);
    }
}
