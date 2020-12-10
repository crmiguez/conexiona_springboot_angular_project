package com.practicas.conexiona.service;

import com.practicas.conexiona.model.Account;
import com.practicas.conexiona.model.User;
import com.practicas.conexiona.repo.AccountRepository;
import com.practicas.conexiona.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;


    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.getAuthorities());
    }

    @Override
    public List<User> findAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(String userId){
        return userRepository.findById(userId);
    }

    @Override
    public User addUser(User user) {
        Account accountExisted = null;
        User userExisted = null;
        List<User> listUsers = (List<User>) userRepository.findAll();

        if (user.getUserId() != null) {
            userExisted = userRepository.findById(user.getUserId()).get();
            return userExisted;
        } else {
            for(User u : listUsers){
                if((u.getUserName().equals(user.getUserName())
                        && u.getEmailAddress().equals(user.getEmailAddress()))
                || (u.getUserName().equals(user.getUserName())
                        || u.getEmailAddress().equals(user.getEmailAddress())))
                    return null;
            }
            accountExisted = accountRepository.findById(user.getAccount().getAccountId()).get();

            if ( accountExisted == null){
                return null;
            } else {
                user.setAccount(accountExisted);
                user.setPassword(bcryptEncoder.encode(user.getPassword()));
                return userRepository.save(user);
            }
        }
    }

    @Override
    public User updateUser(User userDetails, User user){
        Account accountExisted = null;
        User userExisted = null;

        if (user.getUserId() != null) {
            userExisted = userRepository.findById(user.getUserId()).get();
            accountExisted = accountRepository.findById(user.getAccount().getAccountId()).get();

            if ( accountExisted != null){
                user.setEmailAddress(userDetails.getEmailAddress());
                user.setUserName(userDetails.getUserName());
                user.setPassword(bcryptEncoder.encode(user.getPassword()));
                user.setLastLogin(userDetails.getLastLogin());
                user.setAccount(accountExisted);
                user.setEnabled(userDetails.getEnabled());
                return userRepository.save(user);
            }
        }
        return null;
    }

    @Override
    public void deleteUser (User user){
        userRepository.delete(user);
    }
}
