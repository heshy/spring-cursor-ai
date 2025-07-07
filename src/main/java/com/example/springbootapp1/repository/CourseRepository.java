package com.example.springbootapp1.repository;

import com.example.springbootapp1.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
} 