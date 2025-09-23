package br.com.infnet.academicsystemapi.repository;

import br.com.infnet.academicsystemapi.model.Enrollment;
import br.com.infnet.academicsystemapi.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    // JPQL para buscar os alunos aprovados numa determinada disciplina/curso
    @Query("SELECT e.student FROM Enrollment e WHERE e.course.id = :courseId AND e.grade >= 7.00")
    List<Student> findApprovedStudentsByCourse(@Param("courseId") Long courseId);

    // JPQL para buscar os alunos reprovados numa determinada disciplina/curso
    @Query("SELECT e.student FROM Enrollment e WHERE e.course.id = :courseId AND e.grade < 7.00")
    List<Student> findDisapprovedStudentsByCourse(@Param("courseId") Long courseId);
}
