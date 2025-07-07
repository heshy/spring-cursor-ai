package com.example.springbootapp1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal courseFee;

    @ManyToMany(mappedBy = "courses")
    @JsonBackReference
    private Set<Student> students = new HashSet<>();

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getCourseFee() { return courseFee; }
    public void setCourseFee(BigDecimal courseFee) { this.courseFee = courseFee; }
    public Set<Student> getStudents() { return students; }
    public void setStudents(Set<Student> students) { this.students = students; }
} 