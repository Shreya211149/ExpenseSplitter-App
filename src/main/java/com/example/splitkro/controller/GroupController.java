package com.example.splitkro.controller;

import com.example.splitkro.dto.request.GroupRequest;
import com.example.splitkro.dto.response.GroupResponse;
import com.example.splitkro.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(@Valid @RequestBody GroupRequest request) {
        GroupResponse response = groupService.createGroup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponse> getGroupById(@PathVariable Long id) {
        GroupResponse response = groupService.getGroupById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getGroupsByUser(@RequestParam Long userId) {
        List<GroupResponse> response = groupService.getGroupsByUser(userId);
        return ResponseEntity.ok(response);
    }

}
