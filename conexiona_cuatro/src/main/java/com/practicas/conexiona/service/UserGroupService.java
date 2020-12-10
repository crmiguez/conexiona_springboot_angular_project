package com.practicas.conexiona.service;

import com.practicas.conexiona.model.Account;
import com.practicas.conexiona.model.UserGroup;
import com.practicas.conexiona.repo.AccountRepository;
import com.practicas.conexiona.repo.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserGroupService implements IUserGroupService {

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<UserGroup> findAllUserGroups() {
        return (List<UserGroup>) userGroupRepository.findAll();
    }

    @Override
    public Optional<UserGroup> findUserGroupById(String userGroupId){
        return userGroupRepository.findById(userGroupId);
    }

    @Override
    public UserGroup addUserGroup(UserGroup userGroup) {
        Account accountExisted = null;
        UserGroup userGroupExisted = null;
        List<UserGroup> listUserGroups = (List<UserGroup>) userGroupRepository.findAll();

        if (userGroup.getUserGroupId() != null) {
            userGroupExisted = userGroupRepository.findById(userGroup.getUserGroupId()).get();
            return userGroupExisted;
        } else {
            for(UserGroup u : listUserGroups){
                if (u.getUserGroupName().equals(userGroup.getUserGroupName()))
                    return null;
            }
            accountExisted = accountRepository.findById(userGroup.getAccount().getAccountId()).get();

            if ( accountExisted == null){
                return null;
            } else {
                userGroup.setAccount(accountExisted);
                return userGroupRepository.save(userGroup);
            }
        }
    }

    @Override
    public UserGroup updateUserGroup(UserGroup userGroupDetails, UserGroup userGroup){
        Account accountExisted = null;
        UserGroup userGroupExisted = null;

        if (userGroup.getUserGroupId() != null) {
            userGroupExisted = userGroupRepository.findById(userGroup.getUserGroupId()).get();
            accountExisted = accountRepository.findById(userGroup.getAccount().getAccountId()).get();

            if ( accountExisted != null){
                userGroup.setUserGroupName(userGroupDetails.getUserGroupName());
                userGroup.setEnabled(userGroupDetails.getEnabled());
                userGroup.setAccount(accountExisted);
                return userGroupRepository.save(userGroup);
            }
        }
        return null;
    }

    @Override
    public void deleteUserGroup (UserGroup userGroup){
        userGroupRepository.delete(userGroup);
    }
}
