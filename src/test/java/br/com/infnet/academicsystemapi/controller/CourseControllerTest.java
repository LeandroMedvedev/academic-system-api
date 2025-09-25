package br.com.infnet.academicsystemapi.controller;

import br.com.infnet.academicsystemapi.model.Course;
import br.com.infnet.academicsystemapi.model.Enrollment;
import br.com.infnet.academicsystemapi.model.Student;
import br.com.infnet.academicsystemapi.repository.CourseRepository;
import br.com.infnet.academicsystemapi.repository.EnrollmentRepository;
import br.com.infnet.academicsystemapi.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @BeforeEach
    void setUp() {
        enrollmentRepository.deleteAll();
        studentRepository.deleteAll();
        courseRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve retornar a lista de alunos aprovados com status 200 OK")
    @WithMockUser
    void getApprovedStudents_ShouldReturnListOfStudentsAnd200() throws Exception {
        Course ipsDriverTraining = new Course();
        ipsDriverTraining.setName("IPS Driver Training");
        ipsDriverTraining.setCode("IPS101");
        courseRepository.save(ipsDriverTraining);

        Student hollyShumpert = new Student();
        hollyShumpert.setName("Holly Shumpert");
        hollyShumpert.setCpf("555.555.555-55");
        hollyShumpert.setEmail("holly@ips.com");
        studentRepository.save(hollyShumpert);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(hollyShumpert);
        enrollment.setCourse(ipsDriverTraining);
        enrollment.setGrade(new BigDecimal("9.50"));
        enrollmentRepository.save(enrollment);

        mockMvc.perform(get("/api/v1/courses/" + ipsDriverTraining.getId() + "/approved-students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Holly Shumpert")));
    }
}