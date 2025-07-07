package com.example.springbootapp1.service;

import com.example.springbootapp1.dto.StudentRequest;
import com.example.springbootapp1.entity.Student;
import com.example.springbootapp1.entity.Course;
import com.example.springbootapp1.repository.StudentRepository;
import com.example.springbootapp1.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    public Student addStudent(StudentRequest request) {
        Student student = new Student();
        student.setName(request.getName());
        student.setAge(request.getAge());
        student.setTown(request.getTown());
        return studentRepository.save(student);
    }

    public Student registerCourses(Long studentId, List<Long> courseIds) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isEmpty()) {
            throw new RuntimeException("Student not found");
        }
        Student student = studentOpt.get();
        List<Course> courses = courseRepository.findAllById(courseIds);
        student.setCourses(new HashSet<>(courses));
        return studentRepository.save(student);
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
} 