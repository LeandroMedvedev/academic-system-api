package br.com.infnet.academicsystemapi.service;

import br.com.infnet.academicsystemapi.dto.CourseRequestDTO;
import br.com.infnet.academicsystemapi.dto.CourseResponseDTO;
import br.com.infnet.academicsystemapi.exception.BusinessException;
import br.com.infnet.academicsystemapi.exception.ResourceNotFoundException;
import br.com.infnet.academicsystemapi.model.Course;
import br.com.infnet.academicsystemapi.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    @Transactional
    public CourseResponseDTO createCourse(CourseRequestDTO requestDTO) {
        courseRepository.findByCode(requestDTO.code()).ifPresent(c -> {
            throw new BusinessException("Código de disciplina já cadastrado: " + requestDTO.code());
        });

        Course course = new Course();
        mapDtoToEntity(requestDTO, course);
        Course savedCourse = courseRepository.save(course);

        return new CourseResponseDTO(savedCourse);
    }

    @Transactional(readOnly = true)
    public List<CourseResponseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(CourseResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CourseResponseDTO getCourseById(Long id) {
        Course course = findCourseById(id);
        return new CourseResponseDTO(course);
    }

    public Course findCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada com o ID: " + id));
    }

    private void mapDtoToEntity(CourseRequestDTO dto, Course entity) {
        entity.setCode(dto.code());
        entity.setName(dto.name());
    }
}
