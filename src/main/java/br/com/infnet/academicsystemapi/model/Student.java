package br.com.infnet.academicsystemapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    private String address;

    // Student 1:N Enrollment.
    // "mappedBy" indica que o lado "dono" do relacionamento está na classe Enrollment, no campo "student".
    // Isso evita a criação de uma tabela de junção extra, pois já temos a 'enrollments'.
    @OneToMany(mappedBy = "student")
    private Set<Enrollment> enrollments;
}


