package com.example.springbootapp1.controller;

import com.example.springbootapp1.dto.CourseRequest;
import com.example.springbootapp1.entity.Course;
import com.example.springbootapp1.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddCourse_Success() throws Exception {
        // Given
        CourseRequest request = new CourseRequest();
        request.setName("Java Programming");
        request.setCourseFee(new BigDecimal("299.99"));

        Course savedCourse = new Course();
        savedCourse.setId(1L);
        savedCourse.setName("Java Programming");
        savedCourse.setCourseFee(new BigDecimal("299.99"));

        when(courseService.addCourse(any(CourseRequest.class))).thenReturn(savedCourse);

        // When & Then
        mockMvc.perform(post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Java Programming"))
                .andExpect(jsonPath("$.courseFee").value(299.99));
    }

    @Test
    public void testAddCourse_ValidationError_EmptyName() throws Exception {
        // Given
        CourseRequest request = new CourseRequest();
        request.setName("");
        request.setCourseFee(new BigDecimal("299.99"));

        // When & Then
        mockMvc.perform(post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Course name is required"));
    }

    @Test
    public void testAddCourse_ValidationError_NullName() throws Exception {
        // Given
        CourseRequest request = new CourseRequest();
        request.setName(null);
        request.setCourseFee(new BigDecimal("299.99"));

        // When & Then
        mockMvc.perform(post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Course name is required"));
    }

    @Test
    public void testAddCourse_ValidationError_NullCourseFee() throws Exception {
        // Given
        CourseRequest request = new CourseRequest();
        request.setName("Java Programming");
        request.setCourseFee(null);

        // When & Then
        mockMvc.perform(post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.courseFee").value("Course fee is required"));
    }

    @Test
    public void testAddCourse_ValidationError_ZeroCourseFee() throws Exception {
        // Given
        CourseRequest request = new CourseRequest();
        request.setName("Java Programming");
        request.setCourseFee(BigDecimal.ZERO);

        // When & Then
        mockMvc.perform(post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.courseFee").value("Course fee must be positive"));
    }

    @Test
    public void testAddCourse_ValidationError_NegativeCourseFee() throws Exception {
        // Given
        CourseRequest request = new CourseRequest();
        request.setName("Java Programming");
        request.setCourseFee(new BigDecimal("-50.00"));

        // When & Then
        mockMvc.perform(post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.courseFee").value("Course fee must be positive"));
    }

    @Test
    public void testAddCourse_ValidationError_MultipleErrors() throws Exception {
        // Given
        CourseRequest request = new CourseRequest();
        request.setName("");
        request.setCourseFee(BigDecimal.ZERO);

        // When & Then
        mockMvc.perform(post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Course name is required"))
                .andExpect(jsonPath("$.courseFee").value("Course fee must be positive"));
    }

    @Test
    public void testAddCourse_WithWhitespaceName() throws Exception {
        // Given
        CourseRequest request = new CourseRequest();
        request.setName("   ");
        request.setCourseFee(new BigDecimal("299.99"));

        // When & Then
        mockMvc.perform(post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Course name is required"));
    }

    @Test
    public void testAddCourse_WithValidDecimalCourseFee() throws Exception {
        // Given
        CourseRequest request = new CourseRequest();
        request.setName("Advanced Java");
        request.setCourseFee(new BigDecimal("149.50"));

        Course savedCourse = new Course();
        savedCourse.setId(2L);
        savedCourse.setName("Advanced Java");
        savedCourse.setCourseFee(new BigDecimal("149.50"));

        when(courseService.addCourse(any(CourseRequest.class))).thenReturn(savedCourse);

        // When & Then
        mockMvc.perform(post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Advanced Java"))
                .andExpect(jsonPath("$.courseFee").value(149.50));
    }
} 