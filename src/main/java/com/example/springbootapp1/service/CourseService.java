package com.example.springbootapp1.service;

import com.example.springbootapp1.dto.CourseRequest;
import com.example.springbootapp1.entity.Course;
import com.example.springbootapp1.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public Course addCourse(CourseRequest request) {
        Course course = new Course();
        course.setName(request.getName());
        course.setCourseFee(request.getCourseFee());
        return courseRepository.save(course);
    }
} 