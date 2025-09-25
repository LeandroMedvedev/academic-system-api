package br.com.infnet.academicsystemapi.controller;

import br.com.infnet.academicsystemapi.dto.StudentRequestDTO;
import br.com.infnet.academicsystemapi.model.Student;
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

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve criar um estudante e retornar status 201 Created")
    @WithMockUser
    void createStudent_ShouldReturn201() throws Exception {
        StudentRequestDTO deaconPalmerRequest = new StudentRequestDTO(
                "Deacon Palmer", "333.333.333-33", "deacon@ips.com", null, null);

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deaconPalmerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Deacon Palmer")))
                .andExpect(jsonPath("$.email", is("deacon@ips.com")));
    }

    @Test
    @DisplayName("Deve retornar status 400 Bad Request ao tentar criar um estudante com dados inválidos")
    @WithMockUser
    void createStudent_WithInvalidData_ShouldReturn400() throws Exception {
        StudentRequestDTO invalidRequest = new StudentRequestDTO(
                "", "invalid-cpf", "invalid-email", null, null);

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar um estudante por ID e status 200 OK")
    @WithMockUser
    void getStudentById_ShouldReturnStudentAnd200() throws Exception {
        Student spenceOlchin = new Student();
        spenceOlchin.setName("Spence Olchin");
        spenceOlchin.setCpf("444.444.444-44");
        spenceOlchin.setEmail("spence@z-maga.com");
        Student savedStudent = studentRepository.save(spenceOlchin);

        mockMvc.perform(get("/api/v1/students/" + savedStudent.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedStudent.getId().intValue())))
                .andExpect(jsonPath("$.name", is("Spence Olchin")));
    }
}
