package com.practicas.conexiona.service;

import com.practicas.conexiona.model.UserGroup;

import java.util.List;
import java.util.Optional;

public interface IUserGroupService {
    public List<UserGroup> findAllUserGroups();
    public Optional<UserGroup> findUserGroupById(String userGroupId);
    public UserGroup addUserGroup(UserGroup userGroup);
    public UserGroup updateUserGroup(UserGroup userGroupDetails, UserGroup userGroup);
    public void deleteUserGroup (UserGroup userGroup);
}
