package com.nb.globalerp.training.sitebackendglobalerp.api.controller;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CoursePatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CourseRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.CourseCompletionStatusResponse;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.CourseResponse;
import com.nb.globalerp.training.sitebackendglobalerp.services.CourseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
@RestController
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/list")
    public ResponseEntity<List<CourseResponse>> getList() {
        List<CourseResponse> response = courseService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<CourseResponse> getById(@RequestParam @Positive int id) {
        var response = courseService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<Integer> create(@RequestBody @Valid CourseRequest request) {
        var response = courseService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping
    public ResponseEntity<CourseResponse> update(@RequestParam @Positive int id, @RequestBody @Valid CoursePatchRequest request) {
        var response = courseService.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam @Positive int id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
