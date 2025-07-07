package com.example.springbootapp1.repository;

import com.example.springbootapp1.entity.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    @DisplayName("Save and find student by ID")
    void testSaveAndFindById() {
        // Given
        Student student = new Student();
        student.setName("Alice");
        student.setAge(20);
        student.setTown("Wonderland");

        // When
        Student saved = studentRepository.save(student);
        Optional<Student> found = studentRepository.findById(saved.getId());

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Alice");
        assertThat(found.get().getAge()).isEqualTo(20);
        assertThat(found.get().getTown()).isEqualTo("Wonderland");
    }

    @Test
    @DisplayName("Find all students")
    void testFindAll() {
        // Given
        Student student1 = new Student();
        student1.setName("Bob");
        student1.setAge(21);
        student1.setTown("Springfield");

        Student student2 = new Student();
        student2.setName("Charlie");
        student2.setAge(22);
        student2.setTown("Shelbyville");

        studentRepository.save(student1);
        studentRepository.save(student2);

        // When
        List<Student> students = studentRepository.findAll();

        // Then
        assertThat(students).hasSize(2);
        assertThat(students).extracting(Student::getName)
                .containsExactlyInAnyOrder("Bob", "Charlie");
    }
} 