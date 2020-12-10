package com.practicas.conexiona.repo;

import com.practicas.conexiona.model.UserGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository extends CrudRepository<UserGroup, String> {

}
