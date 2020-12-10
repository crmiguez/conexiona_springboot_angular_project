package com.practicas.conexiona.service;

import com.practicas.conexiona.model.UserGroupUser;
import com.practicas.conexiona.model.UserGroupUserId;

import java.util.List;
import java.util.Optional;

public interface IUserGroupUserService {
    public List<UserGroupUser> findAllUserGroupUsers();
    public UserGroupUser addUserGroupUser(UserGroupUser userGroupUser);
    public UserGroupUser updateUserGroupUser(UserGroupUser userGroupUser);
    public void deleteUserGroupUser (UserGroupUserId userGroupUser);
    UserGroupUser selectUserGroupUserById(UserGroupUserId userGroupUserId);
}
