package com.example.springbootapp1.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class StudentRequest {
    @NotBlank(message = "{student.name.required}")
    private String name;
    @Min(value = 1, message = "{student.age.positive}")
    private int age;
    @NotBlank(message = "{student.town.required}")
    private String town;

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getTown() { return town; }
    public void setTown(String town) { this.town = town; }
} 