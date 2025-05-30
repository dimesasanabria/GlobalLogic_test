package com.globalogic.test.web;
/**
 * Controller.java
 * 
 * This class is a REST controller for managing user operations.
 * It provides endpoints to create a user and retrieve a user .
 * 
 * @author dms
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.globalogic.test.entity.User;
import com.globalogic.test.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
@SpringBootApplication
@RestController
@RequestMapping("/api/usuarios")
public class Controller {
    
    @Autowired
    private UserService userService;    

    /**
     * postCreateUser Create a new user.
     * @param usuario
     * @return ResponseEntity<Object> containing the created user or an error message.
     * 
     */
   @PostMapping
    public ResponseEntity<Object> postCreateUser(@RequestBody User usuario){
      return userService.saveService(usuario);
    }
    /**
     * getUser Retrieve a user by ID and token.
     * @param id
     * @param token
     * @return
     */
    @GetMapping("/{id}/{token}")
    public ResponseEntity<Object> getUser(@PathVariable Long id, @PathVariable String token) {
                 return userService.getUser(token,id); 
    }      
}