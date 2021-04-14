package com.msa.userservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select count(u) from User u where u.username = :username")
    long findByUsername(@Param("username") String username);
}
