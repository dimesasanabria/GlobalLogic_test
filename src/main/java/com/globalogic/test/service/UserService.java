package com.globalogic.test.service;


/**     
 * * UserService.java
 *  * This interface defines the contract for user-related operations.
 */
import com.globalogic.test.entity.User;

public interface UserService {
    public User saveService(User usuario)throws Exception;

    public User getUser(String token, Long id);
}
