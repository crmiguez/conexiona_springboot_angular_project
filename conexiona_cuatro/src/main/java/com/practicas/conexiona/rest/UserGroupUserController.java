package com.practicas.conexiona.rest;

import com.practicas.conexiona.exception.ResourceNotFoundException;
import com.practicas.conexiona.model.UserGroupUser;
import com.practicas.conexiona.model.UserGroupUserId;
import com.practicas.conexiona.service.UserGroupUserService;
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
@RequestMapping("/api/v1/usergroupusermanage")
public class UserGroupUserController {

    @Autowired
    private UserGroupUserService userGroupUserService;

    @GetMapping("/usergroupusers")
    public List<UserGroupUser> getAllUserGroupUsers() {
        return userGroupUserService.findAllUserGroupUsers();
    }

    @GetMapping("/usergroupuser/{userId}/{userGroupId}")
    public ResponseEntity<UserGroupUser> getUserGroupById(
            @PathVariable(value = "userId") String userId, @PathVariable(value = "userGroupId") String userGroupId) throws ResourceNotFoundException {
        UserGroupUser userGroupUser = userGroupUserService.selectUserGroupUserById(new UserGroupUserId(userId, userGroupId));
        return new ResponseEntity<UserGroupUser>(userGroupUser,HttpStatus.OK);
    }

    @PostMapping("/usergroupuser")
    public UserGroupUser createuserGroup(@Valid @RequestBody UserGroupUser userGroupUser) { return userGroupUserService.addUserGroupUser(userGroupUser); }

    @PutMapping("/usergroupuserupdate")
    public ResponseEntity<UserGroupUser> updateUserGroup(
            @Valid @RequestBody UserGroupUser userGroupUser) {
        final UserGroupUser updatedUserGroupUser = userGroupUserService.updateUserGroupUser(userGroupUser);
        if  (updatedUserGroupUser == null){
            return new ResponseEntity<UserGroupUser>(HttpStatus.EXPECTATION_FAILED);
        } else {
            return ResponseEntity.ok(updatedUserGroupUser);
        }
    }

    @DeleteMapping("/usergroupuser/{userId}/{userGroupId}")
    public Map<String, Boolean> deleteUserGroupUser(
            @PathVariable(value = "userId") String userId, @PathVariable(value = "userGroupId") String userGroupId) throws Exception { ;
        userGroupUserService.deleteUserGroupUser(new UserGroupUserId(userId, userGroupId));
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
