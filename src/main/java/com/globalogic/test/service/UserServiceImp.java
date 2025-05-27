package com.globalogic.test.service;

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

    public User updateUser(User userUpdated) {
        return this.userRepository.save(userUpdated);
    }
    
}
