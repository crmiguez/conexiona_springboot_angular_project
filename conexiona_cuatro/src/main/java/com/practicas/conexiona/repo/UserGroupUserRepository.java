package com.practicas.conexiona.repo;

import com.practicas.conexiona.model.UserGroupUser;
import com.practicas.conexiona.model.UserGroupUserId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupUserRepository extends CrudRepository<UserGroupUser, UserGroupUserId> {
    List<UserGroupUser> findByUserAdmin(String userAdmin);
    UserGroupUser findByUserGroupUserId(UserGroupUserId userGroupUserId);
    List<UserGroupUser> findByUserGroupUserIdAndUserAdmin(UserGroupUserId userGroupUserId, String userAdmin);


}
