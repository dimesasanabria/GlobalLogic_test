package com.globalogic.test.service;
import java.nio.charset.StandardCharsets;

/**    
 * * This class implements the UserService interface, providing methods to save a user and retrieve a user by token and ID.
 * * It uses a UserRepository to interact with the database and a JwtUtil to handle JWT token generation and validation. 
 * * @author GlobalLogic
 * * @version 1.0           
 * * @author dms
 */
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.globalogic.test.Persistence.UserRepository;
import com.globalogic.test.entity.User;
import com.globalogic.test.exception.ExceptionList;
import com.globalogic.test.exception.ResponseError;
import com.globalogic.test.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

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
    public ResponseEntity<Object> saveService(User usuario){
        try {
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
        usuario.setPassword(new String(usuario.EncryptePassword(), StandardCharsets.UTF_8));
        usuario.setDateCreated(new Timestamp(System.currentTimeMillis()));
        return new ResponseEntity<>(this.userRepository.save(usuario),HttpStatus.CREATED);    
        }catch (RuntimeException er){
            if(er.getMessage() == null || er.getMessage().isEmpty()) {
                er = new RuntimeException("Parameters incorrects in the request");
                    er.printStackTrace();
            }
          
            ResponseError responseError = new ResponseError(er.getMessage(), HttpStatus.BAD_REQUEST.value());
            ExceptionList exceptionList = new ExceptionList();
            exceptionList.addError(responseError);
            return new ResponseEntity<>(exceptionList, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) { 
             if(e.getMessage() == null || e.getMessage().isEmpty()) {
                e.printStackTrace();
                e = new RuntimeException("Parameters are incorrect in the request or missing mandatory parameters");
            }
            ResponseError responseError = new ResponseError(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            ExceptionList exceptionList = new ExceptionList();
            exceptionList.addError(responseError);
            return new ResponseEntity<>(exceptionList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * * @param token
     * * @param id
     * * @return Entity User  unquiry   by ID
     * * This method retrieves a user by their ID and validates the provided JWT token.
     */
    @Override
    public ResponseEntity<Object> getUser(String token, Long id) {
        try {         
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
                return new ResponseEntity<>(userFound, HttpStatus.OK);  
        } catch (ExpiredJwtException e) {
            ResponseError responseError = new ResponseError(e.getMessage(), HttpStatus.UNAUTHORIZED.value());
            ExceptionList exceptionList = new ExceptionList();
            exceptionList.addError(responseError);
            return new ResponseEntity<>(exceptionList, HttpStatus.UNAUTHORIZED);
        }catch (JwtException ewt) {
            ewt.printStackTrace();
            ResponseError responseError = new ResponseError(ewt.getMessage(), HttpStatus.UNAUTHORIZED.value());
            ExceptionList exceptionList = new ExceptionList();
            exceptionList.addError(responseError);
            return new ResponseEntity<>(exceptionList, HttpStatus.UNAUTHORIZED);
       }catch (RuntimeException er) {
            er.printStackTrace();
            ResponseError responseError = new ResponseError(er.getMessage(), HttpStatus.BAD_REQUEST.value());
            ExceptionList exceptionList = new ExceptionList();
            exceptionList.addError(responseError);
            return new ResponseEntity<>(exceptionList, HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
           e.printStackTrace();
            ResponseError responseError = new ResponseError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            ExceptionList exceptionList = new ExceptionList();
            exceptionList.addError(responseError);
            return new ResponseEntity<>(exceptionList, HttpStatus.INTERNAL_SERVER_ERROR);
        }    
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
