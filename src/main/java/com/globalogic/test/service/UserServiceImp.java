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
import com.globalogic.test.persistence.UserRepository;
import com.globalogic.test.entity.User;
import com.globalogic.test.exception.ExceptionInvalidName;
import com.globalogic.test.exception.ExceptionList;
import com.globalogic.test.exception.ExceptionTokenInvalid;
import com.globalogic.test.exception.ResponseError;
import com.globalogic.test.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import com.globalogic.test.exception.ExceptionFormatEmail;
import com.globalogic.test.exception.ExceptionInvalidPassword;
import com.globalogic.test.exception.ExceptionUserIsEmpty;
import com.globalogic.test.exception.ExceptionUserAlreadyExists;
import com.globalogic.test.exception.ExceptionDataNotFound;
import com.globalogic.test.dto.UserDto;
import com.globalogic.test.dto.UserMapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;



@Service
public class UserServiceImp implements UserService {
@Autowired
private UserRepository userRepository;
@Autowired
private JwtUtil jwtUtil;
private final PasswordEncoder passwordEncoder;
    /**
     * * UserService Constructor
     * * Initializes the UserService with a PasswordEncoder for encoding passwords.
     * @param passwordEncoder
     */
    public UserServiceImp(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * * saveService Save a user to the database after validating the email and password.
     * * Generates a JWT token for the user.
     * * @param usuario The user to be saved.
     * * @return The user entity.
     */
    @Override
    public ResponseEntity<Object> saveService(UserDto usuarioDto){
        try {
            //Validations Dto Object
            if(usuarioDto == null) {
                throw new ExceptionUserIsEmpty("The User cannot be null");
            }
            if(!usuarioDto.checkEmail()){
                throw new ExceptionFormatEmail("The format Email is invalid");
            }
            System.out.println("Email---------------------------->"+usuarioDto.getEmail());
              Optional<User> userOptional = this.userRepository.findUserByEmail(usuarioDto.getEmail());
            if (userOptional.isPresent()){ 
                throw new ExceptionUserAlreadyExists("This user already exists");
            }
             if(!usuarioDto.isValidPassword()){
                throw new ExceptionInvalidPassword("Password must contain between 8 and 12 characters, only one uppercase letter, and  two numbers in any position");   
            }
            if(usuarioDto.getName() == null || usuarioDto.getName().isEmpty()) {
                throw new ExceptionInvalidName("Name cannot be null or empty");
            }
            // Convert UserDto to User entity
            User usuario = UserMapper.toEntity(usuarioDto);
            String token = jwtUtil.generateToken(usuario.getEmail());
            if(token == null || token.isEmpty()) {
                throw new ExceptionTokenInvalid("Error generating token");
            }
            usuario.setIsActive(true);
            usuario.setToken(token);    
        //    usuario.setPassword(new String(usuario.EncryptePassword(), StandardCharsets.UTF_8));
        usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
        usuario.setDateCreated(new Timestamp(System.currentTimeMillis()));    
        return new ResponseEntity<>(UserMapper.toDto(this.userRepository.save(usuario)),HttpStatus.CREATED);    
        }catch (RuntimeException er){
            er.printStackTrace();    
            if(er.getMessage() == null || er.getMessage().isEmpty()) {
                er = new RuntimeException("Parameters incorrects in the request");
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
    public ResponseEntity<Object> getUser(String authorizationHeader) {
        try { 
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                throw new RuntimeException("Unauthorized: No token provided or invalid token. Please provide a valid token in the Authorization header.");
            }
            if (!jwtUtil.validateToken(authorizationHeader.substring(7))) {
                throw new ExceptionTokenInvalid("Token is invalid");
            }
            System.out.println("Flag-------------------------------"+authorizationHeader.substring(7));
            String email = jwtUtil.extractUsername(authorizationHeader.substring(7));
            System.out.println("Username from token: " + email);
            User userFound;
            Optional<User> userOptional = this.userRepository.findUserByEmail(email);
            if (userOptional.isPresent()) 
                 userFound = userOptional.get();
            else    
                throw new ExceptionDataNotFound("User not found with email: " + email);
            userFound.setToken(jwtUtil.generateToken(userFound.getEmail()));
            System.out.println("Generate new token: " + userFound.getToken());
            User userUpdated = userFound;
            userUpdated.setLastLogin(new Timestamp(System.currentTimeMillis()));      
            updateUser(userUpdated);               
            return new ResponseEntity<>(UserMapper.toDto(userFound), HttpStatus.OK);

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
