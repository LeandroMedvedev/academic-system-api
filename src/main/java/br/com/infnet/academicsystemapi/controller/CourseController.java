package br.com.infnet.academicsystemapi.controller;

import br.com.infnet.academicsystemapi.dto.CourseRequestDTO;
import br.com.infnet.academicsystemapi.dto.CourseResponseDTO;
import br.com.infnet.academicsystemapi.dto.StudentResponseDTO;
import br.com.infnet.academicsystemapi.service.CourseService;
import br.com.infnet.academicsystemapi.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<CourseResponseDTO> createCourse(@Valid @RequestBody CourseRequestDTO requestDTO) {
        CourseResponseDTO response = courseService.createCourse(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    // Endpoints de Relatório
    @GetMapping("/{id}/approved-students")
    public ResponseEntity<List<StudentResponseDTO>> getApprovedStudents(@PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.getApprovedStudentsByCourse(id));
    }

    @GetMapping("/{id}/disapproved-students")
    public ResponseEntity<List<StudentResponseDTO>> getDisapprovedStudents(@PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.getDisapprovedStudentsByCourse(id));
    }
}
