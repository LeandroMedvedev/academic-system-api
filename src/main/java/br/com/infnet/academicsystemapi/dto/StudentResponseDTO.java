package br.com.infnet.academicsystemapi.dto;

import br.com.infnet.academicsystemapi.model.Student;

public record StudentResponseDTO(
        Long id,
        String name,
        String cpf,
        String email
) {
    public StudentResponseDTO(Student student) {
        this(student.getId(), student.getName(), student.getCpf(), student.getEmail());
    }
}
