package com.nb.globalerp.training.sitebackendglobalerp.api.controller;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.GroupMemberPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.GroupMemberResponse;
import com.nb.globalerp.training.sitebackendglobalerp.services.GroupMemberService;
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
@RequestMapping("/api/v1/group-members")
@RestController
public class GroupMemberController {
    private final GroupMemberService groupMemberService;

    @GetMapping("/{groupId}")
    public ResponseEntity<List<GroupMemberResponse>> getByGroupId(@PathVariable @Positive Integer groupId){
        return new ResponseEntity<>(groupMemberService.getByGroupId(groupId), HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity<List<GroupMemberResponse>> patchByGroupId(@RequestBody @Valid List<GroupMemberPatchRequest> list){
        return new ResponseEntity<>(groupMemberService.patchByGroupId(list), HttpStatus.OK);
    }


    @DeleteMapping()
    public ResponseEntity<Void> delete(@RequestBody @Valid List<GroupMemberPatchRequest> list){
        groupMemberService.delete(list);
        return ResponseEntity.noContent().build();
    }
}
