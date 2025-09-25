package br.com.infnet.academicsystemapi.dto;

import br.com.infnet.academicsystemapi.model.Course;

public record CourseResponseDTO(
        Long id,
        String name,
        String code
) {
    public CourseResponseDTO(Course course) {
        this(course.getId(), course.getName(), course.getCode());
    }
}
