package com.practicas.conexiona.service;

import com.practicas.conexiona.model.*;
import com.practicas.conexiona.repo.UserGroupRepository;
import com.practicas.conexiona.repo.UserGroupUserRepository;
import com.practicas.conexiona.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserGroupUserService implements IUserGroupUserService{
    @Autowired
    private UserGroupUserRepository userGroupUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Override
    public List<UserGroupUser> findAllUserGroupUsers() {
        return (List<UserGroupUser>) userGroupUserRepository.findAll();
    }

    @Override
    public UserGroupUser addUserGroupUser(UserGroupUser userGroupUser) {
        UserGroupUser userGroupUserExisted = userGroupUserRepository.findByUserGroupUserId(userGroupUser.getUserGroupUserId());
        Optional<UserGroup> userGroupExisted = null;
        Optional<User> userExisted = null;

        if (userGroupUserExisted !=null ) {
            return userGroupUserExisted;
        } else {
            userGroupExisted = userGroupRepository.findById(userGroupUser.getUserGroupUserId().getUserGroupId());
            userExisted = userRepository.findById(userGroupUser.getUserGroupUserId().getUserId());

            if(userGroupExisted == null || userExisted == null){
                return null;
            } else {
                userGroupUser.setUserGroup(userGroupExisted.get());
                userGroupUser.setUser(userExisted.get());
                return userGroupUserRepository.save(userGroupUser);
            }
        }
    }

    @Override
    public UserGroupUser updateUserGroupUser(UserGroupUser userGroupUser) {
        UserGroupUser userGroupUserExisted = userGroupUserRepository.findByUserGroupUserId(userGroupUser.getUserGroupUserId());
        Optional<UserGroup>userGroupExisted = userGroupRepository.findById(userGroupUser.getUserGroupUserId().getUserGroupId());
        Optional<User> userExisted = userRepository.findById(userGroupUser.getUserGroupUserId().getUserId());

        UserGroupUser userGroupUserUpdated = null;

        if (userGroupUserExisted != null && userGroupExisted != null && userExisted != null) {
            userGroupUser.setUserGroup(userGroupExisted.get());
            userGroupUser.setUser(userExisted.get());
            if(userGroupUser.getUserAdmin().equals(userGroupUserExisted.getUserAdmin())){
                //userGroupUserUpdated = new UserGroupUser(userGroupUser.getUserGroupUserId(), userGroupUserExisted.getUserAdmin());

                userGroupUserUpdated = new UserGroupUser(userGroupUser.getUserGroupUserId(),
                        userGroupUser.getUserGroup(), userGroupUser.getUser(), userGroupUserExisted.getUserAdmin());

            } else {
                //userGroupUserUpdated = new UserGroupUser(userGroupUser.getUserGroupUserId(), userGroupUser.getUserAdmin());

                userGroupUserUpdated = new UserGroupUser(userGroupUser.getUserGroupUserId(),
                        userGroupUser.getUserGroup(), userGroupUser.getUser(), userGroupUser.getUserAdmin());

            }
            return userGroupUserRepository.save(userGroupUserUpdated);
        }
        return null;
    }

    @Override
    public void deleteUserGroupUser (UserGroupUserId userGroupUserId){
        UserGroupUser userGroupUserExisted = userGroupUserRepository.findByUserGroupUserId(userGroupUserId);
        userGroupUserRepository.delete(userGroupUserExisted);
    }

    @Override
    public UserGroupUser selectUserGroupUserById(UserGroupUserId userGroupUserId) {
        UserGroupUser userGroupUserExisted = userGroupUserRepository.findByUserGroupUserId(userGroupUserId);
        return userGroupUserExisted;
    }


}
