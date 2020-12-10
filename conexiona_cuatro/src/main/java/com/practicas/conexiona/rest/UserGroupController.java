package com.practicas.conexiona.rest;

import com.practicas.conexiona.exception.ResourceNotFoundException;
import com.practicas.conexiona.model.UserGroup;
import com.practicas.conexiona.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/api/v1")
@RequestMapping("/api/v1/usergroupmanage")
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;

    @GetMapping("/usergroups")
    public List<UserGroup> getAlluserGroups() {
        return userGroupService.findAllUserGroups();
    }

    @GetMapping("/usergroups/{id}")
    public ResponseEntity<UserGroup> getUserGroupById(
            @PathVariable(value = "id") String userGroupId) throws ResourceNotFoundException {
        UserGroup userGroup = userGroupService.findUserGroupById(userGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("userGroup not found on :: "+ userGroupId));
        return ResponseEntity.ok().body(userGroup);
    }

    @PostMapping("/usergroup")
    public UserGroup createuserGroup(@Valid @RequestBody UserGroup userGroup) { return userGroupService.addUserGroup(userGroup); }

    @PutMapping("/usergroups/{id}")
    public ResponseEntity<UserGroup> updateUserGroup(
            @PathVariable(value = "id") String userGroupId,
            @Valid @RequestBody UserGroup userGroupDetails) throws ResourceNotFoundException {
        UserGroup userGroup = userGroupService.findUserGroupById(userGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("userGroup not found on :: "+ userGroupId));

        final UserGroup updatedUserGroup = userGroupService.updateUserGroup(userGroupDetails, userGroup);
        if  (updatedUserGroup == null){
            return new ResponseEntity<UserGroup>(HttpStatus.EXPECTATION_FAILED);
        } else {
            return ResponseEntity.ok(updatedUserGroup);
        }
    }

    @DeleteMapping("/usergroup/{id}")
    public Map<String, Boolean> deleteuserGroup(
            @PathVariable(value = "id") String userGroupId) throws Exception {
        UserGroup userGroup = userGroupService.findUserGroupById(userGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("userGroup not found on :: "+ userGroupId));

        userGroupService.deleteUserGroup(userGroup);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


}
