-- Tabela "users" (professores) do sistema.
-- Simplificada para focar na autenticação.
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

-- Tabela para armazenar os dados dos alunos.
CREATE TABLE students (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          cpf VARCHAR(14) NOT NULL UNIQUE,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          phone_number VARCHAR(20),
                          address VARCHAR(255)
);

-- Tabela para armazenar as disciplinas.
CREATE TABLE courses (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         code VARCHAR(50) NOT NULL UNIQUE
);

-- Tabela associativa para matrículas (relação N:N entre students e courses).
-- Contém a nota do aluno numa disciplina específica.
CREATE TABLE enrollments (
                             id BIGSERIAL PRIMARY KEY,
                             student_id BIGINT NOT NULL,
                             course_id BIGINT NOT NULL,
                             grade DECIMAL(4, 2), -- Ex: 10.00, 7.50

    -- Chaves Estrangeiras que conectam as tabelas
                             CONSTRAINT fk_enrollment_to_student
                                 FOREIGN KEY (student_id)
                                     REFERENCES students(id)
                                     ON DELETE RESTRICT, -- Impede exclusão de aluno com matrícula

                             CONSTRAINT fk_enrollment_to_course
                                 FOREIGN KEY (course_id)
                                     REFERENCES courses(id)
                                     ON DELETE RESTRICT, -- Impede exclusão de disciplina com matrícula

    -- Garante que um aluno não pode se matricular duas vezes na mesma disciplina
                             CONSTRAINT uq_student_course UNIQUE (student_id, course_id),

    -- Garante que a nota esteja num intervalo válido
                             CONSTRAINT chk_grade_range CHECK (grade >= 0.00 AND grade <= 10.00)
);
