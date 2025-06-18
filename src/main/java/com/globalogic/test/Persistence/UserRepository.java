package com.globalogic.test.persistence;
import java.util.Optional;

/**
 * * UserRepository.java
 * * This interface extends JpaRepository to provide CRUD operations for User entities.
 * * It includes a custom query method to find a user by their email.
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.globalogic.test.entity.User;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
 
     Optional<User> findUserByEmail(String email);
     Boolean existsByEmail(String email);
}
