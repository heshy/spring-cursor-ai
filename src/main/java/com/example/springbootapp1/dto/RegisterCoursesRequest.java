package com.example.springbootapp1.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class RegisterCoursesRequest {
    @NotNull(message = "{student.id.required}")
    private Long studentId;
    @NotEmpty(message = "{course.ids.required}")
    private List<Long> courseIds;

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public List<Long> getCourseIds() { return courseIds; }
    public void setCourseIds(List<Long> courseIds) { this.courseIds = courseIds; }
} 