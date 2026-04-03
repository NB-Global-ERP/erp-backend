package com.nb.globalerp.training.sitebackendglobalerp.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/students")
@Tag(name = "Students", description = "Students operations")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService userService) {
        this.studentService = userService;
    }

    // GET /students
    @Operation(summary = "Получить список студентов")
    @GetMapping
    public List<String> getAllStudents() {
        return List.of("Student 1", "Student 2");
    }

    // GET /students/{id}
    @Operation(summary = "Получить студента по ID")
    @GetMapping("/{id}")
    public String getStudentById(@PathVariable Long id) {
        return "Student with id = " + id;
    }

    // POST /students
    @Operation(summary = "Создать студента")
    @PostMapping
    public String createStudent(@RequestBody String body) {
        return "Student created: " + body;
    }

    // PUT /students/{id}
    @Operation(summary = "Обновить студента")
    @PutMapping("/{id}")
    public String updateStudent(@PathVariable Long id, @RequestBody String body) {
        return "Student updated: " + id;
    }

    // DELETE /students/{id}
    @Operation(summary = "Удалить студента")
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        return "Student deleted: " + id;
    }

    // GET /students/search?query=
    @Operation(summary = "Поиск студентов")
    @GetMapping("/search")
    public List<String> searchStudents(@RequestParam String query) {
        return List.of("Found: " + query);
    }
}