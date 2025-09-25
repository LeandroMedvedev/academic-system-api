package br.com.infnet.academicsystemapi.controller;

import br.com.infnet.academicsystemapi.dto.EnrollmentRequestDTO;
import br.com.infnet.academicsystemapi.dto.GradeRequestDTO;
import br.com.infnet.academicsystemapi.model.Course;
import br.com.infnet.academicsystemapi.model.Student;
import br.com.infnet.academicsystemapi.repository.CourseRepository;
import br.com.infnet.academicsystemapi.repository.EnrollmentRepository;
import br.com.infnet.academicsystemapi.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EnrollmentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    private Student savedStudent;
    private Course savedCourse;

    @BeforeEach
    void setUp() {
        enrollmentRepository.deleteAll();
        studentRepository.deleteAll();
        courseRepository.deleteAll();

        Student student = new Student();
        student.setName("Carrie Heffernan");
        student.setCpf("222.222.222-22");
        student.setEmail("carrie@lawfirm.com");
        savedStudent = studentRepository.save(student);

        Course course = new Course();
        course.setName("Contract Law");
        course.setCode("LAW202");
        savedCourse = courseRepository.save(course);
    }

    @Test
    @DisplayName("Deve matricular um aluno e retornar status 204 No Content")
    @WithMockUser
    void enrollStudent_ShouldReturn204() throws Exception {
        EnrollmentRequestDTO request = new EnrollmentRequestDTO(savedStudent.getId(), savedCourse.getId());

        mockMvc.perform(post("/api/v1/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve atribuir uma nota e retornar status 204 No Content")
    @WithMockUser
    void assignGrade_ShouldReturn204() throws Exception {
        enrollStudent_ShouldReturn204();

        GradeRequestDTO gradeRequest = new GradeRequestDTO(new BigDecimal("8.75"));

        String url = String.format("/api/v1/enrollments/student/%d/course/%d/grade",
                savedStudent.getId(), savedCourse.getId());

        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gradeRequest)))
                .andExpect(status().isNoContent());
    }
}
