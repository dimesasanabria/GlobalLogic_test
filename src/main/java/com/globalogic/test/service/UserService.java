package com.globalogic.test.service;


import org.springframework.http.ResponseEntity;

/**     
 * * UserService.java
 *  * This interface defines the contract for user-related operations.
 */
import com.globalogic.test.entity.User;

public interface UserService {
    public ResponseEntity<Object>  saveService(User usuario);

    public ResponseEntity<Object> getUser(String token, Long id);
}
