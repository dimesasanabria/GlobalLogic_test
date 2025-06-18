package com.globalogic.test.service;

import com.globalogic.test.dto.UserDto;
import com.globalogic.test.dto.UserMapper;    
import org.springframework.http.ResponseEntity;

/**     
 * * UserService.java
 *  * This interface defines the contract for user-related operations.
 */
import com.globalogic.test.entity.User;

public interface UserService {
    public ResponseEntity<Object>  saveService(UserDto usuario);

    public ResponseEntity<Object> getUser(String authorizationHeader);
}
