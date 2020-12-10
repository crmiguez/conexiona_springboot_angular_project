package com.practicas.conexiona.service;

import com.practicas.conexiona.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    public List<User> findAllUsers();
    public Optional<User> findUserById(String userId);
    public User addUser(User user);
    public User updateUser(User userDetails, User user);
    public void deleteUser (User user);

}
