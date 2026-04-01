package com.example.splitkro.service;

import com.example.splitkro.dto.request.GroupRequest;
import com.example.splitkro.dto.response.GroupResponse;
import com.example.splitkro.exception.GroupNotFoundException;
import com.example.splitkro.exception.UserNotFoundException;
import com.example.splitkro.model.Group;
import com.example.splitkro.model.User;
import com.example.splitkro.repository.GroupRepository;
import com.example.splitkro.repository.UserRepository;
import com.example.splitkro.transformer.GroupTransformer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    @Autowired
   private GroupRepository groupRepository;
    @Autowired
   private UserRepository userRepository;

    public GroupResponse createGroup(@Valid GroupRequest groupRequest) {
        User creator = userRepository.findById(groupRequest.getCreatorId())
                .orElseThrow(() -> new UserNotFoundException("Creator not found"));
        List<User> members = userRepository.findAllById(groupRequest.getMemberIds());
        Group group= GroupTransformer.groupReqToGroup(groupRequest,creator,members);
        Group saved = groupRepository.save(group);
        return GroupTransformer.groupToGroupResponse(saved);
    }
    public GroupResponse getGroupById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new GroupNotFoundException("Group doesn't exist"));
        return GroupTransformer.groupToGroupResponse(group);
    }

    public List<GroupResponse> getGroupsByUser(Long userId) {
        return groupRepository.findByMembersId(userId)
                .stream()
                .map(GroupTransformer::groupToGroupResponse)
                .collect(Collectors.toList());
    }


}
