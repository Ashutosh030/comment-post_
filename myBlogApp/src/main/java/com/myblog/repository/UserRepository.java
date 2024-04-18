package com.myblog.repository;

import com.myblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    //here build custom classes->

    Optional<User> findByEmail(String email); //this will take email id, and return optional object if found

    Optional<User> findByUsernameOrEmail (String username, String email);

    Optional<User> findByUsername (String username);

    Boolean  existsByusername (String username); //it will check user exists or not? not exists then create user->

    Boolean  existsByEmail (String email);


}
