
package com.globalogic.test.service;
/**    
 * * This class implements the UserService interface, providing methods to save a user and retrieve a user by token and ID.
 * * It uses a UserRepository to interact with the database and a JwtUtil to handle JWT token generation and validation. 
 * * @author GlobalLogic
 * * @version 1.0           
 * * @author dms
 */
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globalogic.test.Persistence.UserRepository;
import com.globalogic.test.entity.User;
import com.globalogic.test.util.JwtUtil;

@Service
public class UserServiceImp implements UserService {
@Autowired
private UserRepository userRepository;
@Autowired
private JwtUtil jwtUtil;
    /**
     * * saveService Save a user to the database after validating the email and password.
     * * Generates a JWT token for the user.
     * * @param usuario The user to be saved.
     * * @return The user entity.
     */
    @Override
    public User saveService(User usuario) {
        if(this.userRepository.findUserByEmail(usuario.getEmail()) != null) {
            throw new RuntimeException("This user already exists");
        }
        if(!usuario.checkEmail()){
            throw new RuntimeException("The format Email is invalid");
        }
        String token = jwtUtil.generateToken(usuario.getEmail());
        if(token == null || token.isEmpty()) {
            throw new RuntimeException("Error generating token");
        }
        usuario.setIsActive(true);
        usuario.setToken(token);    
        if(!usuario.isValidPassword()){
            throw new RuntimeException("Password must contain between 8 and 12 characters, only two uppercase letter, only one number");   
        }
        usuario.setDateCreated(new Timestamp(System.currentTimeMillis()));
        return this.userRepository.save(usuario);
    }
    /**
     * * @param token
     * * @param id
     * * @return Entity User  unquiry   by ID
     * * This method retrieves a user by their ID and validates the provided JWT token.
     */
    @Override
    public User getUser(String token, Long id) {
        if (token == null || token.isEmpty()) {
            throw new RuntimeException("Token is empty");
        }
        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Token is invalid");
        }
        User userFound =  this.userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User userUpdated = userFound;
        userUpdated.setLastLogin(new Timestamp(System.currentTimeMillis()));      
        updateUser(userUpdated);   
        return userFound;       
    }
    /**
     * * updateUser Update an existing user in the database with the last time  that was inquired.
     * * @param userUpdated The user entity with updated information.
     * * @return The updated user entity.
     */
    public User updateUser(User userUpdated) {
        return this.userRepository.save(userUpdated);
    }
    
}
