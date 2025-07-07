package com.example.springbootapp1.repository;

import com.example.springbootapp1.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
} 