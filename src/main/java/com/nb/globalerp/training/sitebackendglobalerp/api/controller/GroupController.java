package com.nb.globalerp.training.sitebackendglobalerp.api.controller;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.GroupPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.GroupRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.*;
import com.nb.globalerp.training.sitebackendglobalerp.services.GroupService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
@RequestMapping("/api/v1/groups")
@RestController
public class GroupController {

    private final GroupService groupService;

    @GetMapping("/list")
    public ResponseEntity<List<GroupResponse>> getList() {
        List<GroupResponse> response = groupService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<GroupResponse> getById(@RequestParam @Positive int id) {
        var response = groupService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/analytics/count")
    public ResponseEntity<CountResponse> rowsNum(){
        return new ResponseEntity<>(new CountResponse(groupService.getGroupNum()), HttpStatus.OK);
    }

    @GetMapping("/analytics/basicStats/pricePerPerson")
    public ResponseEntity<SimpleStatsResponse> pricePerPersonStats(){
        return new ResponseEntity<>(groupService.getPricePerPersonStats(), HttpStatus.OK);
    }

    @GetMapping("/analytics/basicStats/timeStats")
    public ResponseEntity<SimpleStatsResponse> timeStats(){
        return new ResponseEntity<>(groupService.getTimeStats(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CreateResponse> create(@RequestBody @Valid GroupRequest request) {
        var id = groupService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateResponse(id));
    }

    @PatchMapping
    public ResponseEntity<GroupResponse> update(@RequestParam @Positive int id, @RequestBody @Valid GroupPatchRequest request) {
        var response = groupService.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam @Positive int id) {
        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{groupId}/add/student/{studentId}")
    public ResponseEntity<Void> addStudentToGroup(
        @RequestParam @NotNull @Positive Integer groupId,
        @RequestParam @NotNull @Positive Integer studentId
    ) {
        groupService.addStudentToGroup(groupId, studentId);
        return ResponseEntity.noContent().build();
    }
}
