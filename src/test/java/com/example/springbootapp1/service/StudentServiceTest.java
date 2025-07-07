package com.example.springbootapp1.service;

import com.example.springbootapp1.dto.StudentRequest;
import com.example.springbootapp1.entity.Student;
import com.example.springbootapp1.entity.Course;
import com.example.springbootapp1.repository.StudentRepository;
import com.example.springbootapp1.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private StudentService studentService;

    private StudentRequest studentRequest;
    private Student student;
    private Course course1;
    private Course course2;

    @BeforeEach
    void setUp() {
        // Setup test data
        studentRequest = new StudentRequest();
        studentRequest.setName("John Doe");
        studentRequest.setAge(22);
        studentRequest.setTown("New York");

        student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setAge(22);
        student.setTown("New York");

        course1 = new Course();
        course1.setId(1L);
        course1.setName("Java Programming");

        course2 = new Course();
        course2.setId(2L);
        course2.setName("Spring Boot");
    }

    @Test
    void testAddStudent_Success() {
        // Given
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        // When
        Student result = studentService.addStudent(studentRequest);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals(22, result.getAge());
        assertEquals("New York", result.getTown());

        // Verify that save was called with correct student data
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void testAddStudent_VerifyStudentCreation() {
        // Given
        when(studentRepository.save(any(Student.class))).thenAnswer(invocation -> {
            Student savedStudent = invocation.getArgument(0);
            savedStudent.setId(1L);
            return savedStudent;
        });

        // When
        Student result = studentService.addStudent(studentRequest);

        // Then
        verify(studentRepository).save(argThat(student -> 
            student.getName().equals("John Doe") &&
            student.getAge() == 22 &&
            student.getTown().equals("New York")
        ));
    }

    @Test
    void testGetStudentById_Success() {
        // Given
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        // When
        Optional<Student> result = studentService.getStudentById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("John Doe", result.get().getName());
        verify(studentRepository).findById(1L);
    }

    @Test
    void testGetStudentById_NotFound() {
        // Given
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        // When
        Optional<Student> result = studentService.getStudentById(99L);

        // Then
        assertFalse(result.isPresent());
        verify(studentRepository).findById(99L);
    }

    @Test
    void testRegisterCourses_Success() {
        // Given
        Long studentId = 1L;
        List<Long> courseIds = Arrays.asList(1L, 2L);
        List<Course> courses = Arrays.asList(course1, course2);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findAllById(courseIds)).thenReturn(courses);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        // When
        Student result = studentService.registerCourses(studentId, courseIds);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());

        // Verify interactions
        verify(studentRepository).findById(studentId);
        verify(courseRepository).findAllById(courseIds);
        verify(studentRepository).save(student);
    }

    @Test
    void testRegisterCourses_StudentNotFound() {
        // Given
        Long studentId = 99L;
        List<Long> courseIds = Arrays.asList(1L, 2L);

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentService.registerCourses(studentId, courseIds);
        });

        assertEquals("Student not found", exception.getMessage());

        // Verify interactions
        verify(studentRepository).findById(studentId);
        verify(courseRepository, never()).findAllById(any());
        verify(studentRepository, never()).save(any());
    }

    @Test
    void testRegisterCourses_EmptyCourseList() {
        // Given
        Long studentId = 1L;
        List<Long> courseIds = new ArrayList<>();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findAllById(courseIds)).thenReturn(new ArrayList<>());
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        // When
        Student result = studentService.registerCourses(studentId, courseIds);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());

        // Verify interactions
        verify(studentRepository).findById(studentId);
        verify(courseRepository).findAllById(courseIds);
        verify(studentRepository).save(student);
    }

    @Test
    void testRegisterCourses_SingleCourse() {
        // Given
        Long studentId = 1L;
        List<Long> courseIds = Arrays.asList(1L);
        List<Course> courses = Arrays.asList(course1);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findAllById(courseIds)).thenReturn(courses);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        // When
        Student result = studentService.registerCourses(studentId, courseIds);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());

        // Verify interactions
        verify(studentRepository).findById(studentId);
        verify(courseRepository).findAllById(courseIds);
        verify(studentRepository).save(student);
    }

    @Test
    void testRegisterCourses_VerifyCoursesSet() {
        // Given
        Long studentId = 1L;
        List<Long> courseIds = Arrays.asList(1L, 2L);
        List<Course> courses = Arrays.asList(course1, course2);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findAllById(courseIds)).thenReturn(courses);
        when(studentRepository.save(any(Student.class))).thenAnswer(invocation -> {
            Student savedStudent = invocation.getArgument(0);
            return savedStudent;
        });

        // When
        Student result = studentService.registerCourses(studentId, courseIds);

        // Then
        verify(studentRepository).save(argThat(student -> 
            student.getCourses().size() == 2 &&
            student.getCourses().contains(course1) &&
            student.getCourses().contains(course2)
        ));
    }
} 