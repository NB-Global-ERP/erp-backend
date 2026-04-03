package com.nb.globalerp.training.sitebackendglobalerp.api.controller;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.StudentPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.StudentRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.CreateResponse;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.StudentResponse;
import com.nb.globalerp.training.sitebackendglobalerp.services.StudentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/students")
@RestController
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getById(@PathVariable @Positive Integer id) {
        var response = studentService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAll(){
        var response = studentService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<CreateResponse> create(@RequestBody @Valid StudentRequest request) {
        var id = studentService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateResponse(id));
    }

    @PatchMapping
    public ResponseEntity<StudentResponse> update(@RequestParam @Positive Integer id, @RequestBody @Valid StudentPatchRequest request) {
        var response = studentService.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam @Positive Integer id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
