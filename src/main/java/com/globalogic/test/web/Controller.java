package com.globalogic.test.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.globalogic.test.entity.User;
import com.globalogic.test.exception.ExceptionList;
import com.globalogic.test.exception.ResponseError;
import com.globalogic.test.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/usuarios")
public class Controller {
    
    @Autowired
    private UserService userService;

   @PostMapping
    public ResponseEntity<Object> postCreateUser(@RequestBody User usuario) {
        try {
                return new ResponseEntity<>(userService.saveService(usuario),HttpStatus.CREATED);    
        } catch (Exception e) {
            // TODO: handle exception
            ResponseError responseError = new ResponseError(e.getMessage(), HttpStatus.NOT_FOUND.value());
              ExceptionList exceptionList = new ExceptionList();
            exceptionList.addError(responseError);
            return new ResponseEntity<>(exceptionList, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{id}/{token}")
    public ResponseEntity<Object> getUser(@PathVariable Long id, @PathVariable String token) {
       try {
                 return new ResponseEntity<>(userService.getUser(token,id), HttpStatus.OK);    
       } catch (ExpiredJwtException e) {
        e.printStackTrace();
            ResponseError responseError = new ResponseError(e.getMessage(), HttpStatus.UNAUTHORIZED.value());
            ExceptionList exceptionList = new ExceptionList();
            exceptionList.addError(responseError);
            return new ResponseEntity<>(exceptionList, HttpStatus.UNAUTHORIZED);
            
       } catch (Exception e) {
            ResponseError responseError = new ResponseError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            ExceptionList exceptionList = new ExceptionList();
            exceptionList.addError(responseError);
            return new ResponseEntity<>(exceptionList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }                   
}
