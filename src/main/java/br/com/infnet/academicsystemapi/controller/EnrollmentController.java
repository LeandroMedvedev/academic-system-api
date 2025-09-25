package br.com.infnet.academicsystemapi.controller;

import br.com.infnet.academicsystemapi.dto.EnrollmentRequestDTO;
import br.com.infnet.academicsystemapi.dto.GradeRequestDTO;
import br.com.infnet.academicsystemapi.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<Void> enrollStudent(@Valid @RequestBody EnrollmentRequestDTO requestDTO) {
        enrollmentService.enrollStudent(requestDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/student/{studentId}/course/{courseId}/grade")
    public ResponseEntity<Void> assignGrade(@PathVariable Long studentId,
                                            @PathVariable Long courseId,
                                            @Valid @RequestBody GradeRequestDTO requestDTO) {
        enrollmentService.assignGrade(studentId, courseId, requestDTO);
        return ResponseEntity.noContent().build();
    }
}
