package com.example.springbootapp1.controller;

import com.example.springbootapp1.entity.Student;
import com.example.springbootapp1.dto.StudentRequest;
import com.example.springbootapp1.service.StudentService;
import com.example.springbootapp1.dto.RegisterCoursesRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping
    public Student addStudent(@Valid @RequestBody StudentRequest request) {
        return studentService.addStudent(request);
    }

    @PostMapping("/register-courses")
    public Student registerCourses(@Valid @RequestBody RegisterCoursesRequest request) {
        return studentService.registerCourses(request.getStudentId(), request.getCourseIds());
    }

    @GetMapping("/{id}")
    public Optional<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
} 