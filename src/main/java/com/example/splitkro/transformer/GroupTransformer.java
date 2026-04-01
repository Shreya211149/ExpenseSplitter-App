package com.example.splitkro.transformer;

import com.example.splitkro.dto.request.GroupRequest;
import com.example.splitkro.dto.response.GroupResponse;
import com.example.splitkro.model.Group;
import com.example.splitkro.model.User;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

public class GroupTransformer {
    public static Group groupReqToGroup(@Valid GroupRequest groupRequest, User creator, List<User> members) {
        return Group.builder()
                .name(groupRequest.getName())
                .creator(creator)
                .members(members)
                .build();
    }


    public static GroupResponse groupToGroupResponse(Group savedGroup) {
        return GroupResponse.builder()
                .id(savedGroup.getId())
                .name(savedGroup.getName())
                .createdAt(savedGroup.getCreatedAt())
                .creatorName(savedGroup.getCreator().getName())
                .memberNames(savedGroup.getMembers()
                        .stream()
                        .map(User::getName)
                        .collect(Collectors.toList()))
                .build();
    }
}
