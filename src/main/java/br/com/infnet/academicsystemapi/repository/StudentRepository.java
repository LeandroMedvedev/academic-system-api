package br.com.infnet.academicsystemapi.repository;

import br.com.infnet.academicsystemapi.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByCpf(String cpf);
    Optional<Student> findByEmail(String email);
}
