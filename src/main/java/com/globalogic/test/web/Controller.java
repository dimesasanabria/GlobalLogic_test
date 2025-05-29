package com.globalogic.test.web;
/**
 * Controller.java
 * 
 * This class is a REST controller for managing user operations.
 * It provides endpoints to create a user and retrieve a user .
 * 
 * @author dms
 */

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.globalogic.test.entity.User;
import com.globalogic.test.exception.ExceptionList;
import com.globalogic.test.exception.ResponseError;
import com.globalogic.test.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

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
     * @return
     */
   @PostMapping
    public ResponseEntity<Object> postCreateUser(@RequestBody User usuario) {
        try {
                return new ResponseEntity<>(userService.saveService(usuario),HttpStatus.CREATED);    
        
        }catch (RuntimeException er){
            ResponseError responseError = new ResponseError(er.getMessage(), HttpStatus.BAD_REQUEST.value());
            ExceptionList exceptionList = new ExceptionList();
            exceptionList.addError(responseError);
            return new ResponseEntity<>(exceptionList, HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            ResponseError responseError = new ResponseError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            ExceptionList exceptionList = new ExceptionList();
            exceptionList.addError(responseError);
            return new ResponseEntity<>(exceptionList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * getUser Retrieve a user by ID and token.
     * @param id
     * @param token
     * @return
     */
    @GetMapping("/{id}/{token}")
    public ResponseEntity<Object> getUser(@PathVariable Long id, @PathVariable String token) {
       try {
                 return new ResponseEntity<>(userService.getUser(token,id), HttpStatus.OK);    
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
}
