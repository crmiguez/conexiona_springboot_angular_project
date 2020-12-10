package com.practicas.conexiona.repo;

import com.practicas.conexiona.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User findByUserName(String userName);
}
