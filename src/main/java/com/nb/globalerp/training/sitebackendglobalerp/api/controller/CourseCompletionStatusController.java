package com.nb.globalerp.training.sitebackendglobalerp.api.controller;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CourseCompletionStatusPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CourseCompletionStatusRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.CourseCompletionStatusResponse;
import com.nb.globalerp.training.sitebackendglobalerp.services.CourseCompletionStatusService;
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

@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/course-completion-statuses")
@RestController
public class CourseCompletionStatusController {

    private final CourseCompletionStatusService courseCompletionStatusService;

    @GetMapping
    public ResponseEntity<CourseCompletionStatusResponse> getById(@RequestParam @Positive int id) {
        var response = courseCompletionStatusService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<Integer> create(@RequestBody @Valid CourseCompletionStatusRequest request) {
        var response = courseCompletionStatusService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping
    public ResponseEntity<CourseCompletionStatusResponse> update(@RequestParam @Positive int id, @RequestBody @Valid CourseCompletionStatusPatchRequest request) {
        var response = courseCompletionStatusService.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam @Positive int id) {
        courseCompletionStatusService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
