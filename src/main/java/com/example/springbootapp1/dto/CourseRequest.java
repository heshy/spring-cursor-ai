package com.example.springbootapp1.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CourseRequest {
    @NotBlank(message = "{course.name.required}")
    private String name;
    @NotNull(message = "{course.fee.required}")
    @DecimalMin(value = "0.0", inclusive = false, message = "{course.fee.positive}")
    private BigDecimal courseFee;

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getCourseFee() { return courseFee; }
    public void setCourseFee(BigDecimal courseFee) { this.courseFee = courseFee; }
} 