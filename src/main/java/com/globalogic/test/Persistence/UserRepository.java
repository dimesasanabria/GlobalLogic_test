package com.globalogic.test.Persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.globalogic.test.entity.User;

public interface UserRepository  extends JpaRepository<User, Long> {
    // Custom query methods can be defined here if needed
    // For example:
    // List<User> findByLastName(String lastName);
    @Query("SELECT p FROM User p WHERE p.email = :email")
    User findUserByEmail(@Param("email") String email);
}
