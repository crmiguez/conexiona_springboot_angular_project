package com.practicas.conexiona.rest;

import com.practicas.conexiona.exception.ResourceNotFoundException;
import com.practicas.conexiona.model.User;
import com.practicas.conexiona.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin()
@RestController
//@RequestMapping("/api/v1")
@RequestMapping("/api/v1/usermanage")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_GUEST') OR hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(
            @PathVariable(value = "id") String userId) throws ResourceNotFoundException {
        User user = userService.findUserById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found on :: "+ userId));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user) { return userService.addUser(user); }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable(value = "id") String userId,
            @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        User user = userService.findUserById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found on :: "+ userId));

        final User updatedUser = userService.updateUser(userDetails, user);
        if  (updatedUser == null){
            return new ResponseEntity<User>(HttpStatus.EXPECTATION_FAILED);
        } else {
            return ResponseEntity.ok(updatedUser);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/user/{id}")
    public Map<String, Boolean> deleteUser(
            @PathVariable(value = "id") String userId) throws Exception {
        User user = userService.findUserById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found on :: "+ userId));

        userService.deleteUser(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
