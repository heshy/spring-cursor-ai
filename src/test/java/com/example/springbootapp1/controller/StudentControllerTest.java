package com.example.springbootapp1.controller;

import com.example.springbootapp1.dto.StudentRequest;
import com.example.springbootapp1.entity.Student;
import com.example.springbootapp1.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddStudent_Success() throws Exception {
        // Given
        StudentRequest request = new StudentRequest();
        request.setName("John Doe");
        request.setAge(22);
        request.setTown("New York");

        Student savedStudent = new Student();
        savedStudent.setId(1L);
        savedStudent.setName("John Doe");
        savedStudent.setAge(22);
        savedStudent.setTown("New York");

        when(studentService.addStudent(any(StudentRequest.class))).thenReturn(savedStudent);

        // When & Then
        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.age").value(22))
                .andExpect(jsonPath("$.town").value("New York"));
    }

    @Test
    public void testAddStudent_ValidationError_EmptyName() throws Exception {
        // Given
        StudentRequest request = new StudentRequest();
        request.setName("");
        request.setAge(22);
        request.setTown("New York");

        // When & Then
        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name is required"));
    }

    @Test
    public void testAddStudent_ValidationError_InvalidAge() throws Exception {
        // Given
        StudentRequest request = new StudentRequest();
        request.setName("John Doe");
        request.setAge(0);
        request.setTown("New York");

        // When & Then
        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.age").value("Age must be positive"));
    }

    @Test
    public void testAddStudent_ValidationError_EmptyTown() throws Exception {
        // Given
        StudentRequest request = new StudentRequest();
        request.setName("John Doe");
        request.setAge(22);
        request.setTown("");

        // When & Then
        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.town").value("Town is required "));
    }

    @Test
    public void testGetStudentById_Success() throws Exception {
        // Given
        Student student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setAge(22);
        student.setTown("New York");

        when(studentService.getStudentById(1L)).thenReturn(Optional.of(student));

        // When & Then
        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.age").value(22))
                .andExpect(jsonPath("$.town").value("New York"));
    }

    @Test
    public void testGetStudentById_NotFound() throws Exception {
        // Given
        when(studentService.getStudentById(99L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/students/99"))
                .andExpect(status().isOk())
                .andExpect(content().string("null")); // Returns null when Optional is empty
    }
} 