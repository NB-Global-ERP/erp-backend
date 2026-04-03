package com.nb.globalerp.training.sitebackendglobalerp.api.controller;

import com.nb.globalerp.training.sitebackendglobalerp.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/students")
@RestController
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<String> getAllStudents() {
        return List.of("Student 1", "Student 2");
    }

    @GetMapping("/{id}")
    public String getStudentById(@PathVariable Long id) {
        return "Student with id = " + id;
    }

    @PostMapping
    public String createStudent(@RequestBody String body) {
        return "Student created: " + body;
    }

    @PutMapping("/{id}")
    public String updateStudent(@PathVariable Long id, @RequestBody String body) {
        return "Student updated: " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        return "Student deleted: " + id;
    }

    @GetMapping("/search")
    public List<String> searchStudents(@RequestParam String query) {
        return List.of("Found: " + query);
    }
}