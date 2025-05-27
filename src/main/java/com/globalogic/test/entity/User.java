package com.globalogic.test.entity;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name")
    private String name; 
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "token", nullable = false)
    private String token;
    @Column(name = "isActive", nullable = false)
    private Boolean isActive;
    @Column(name = "lastLogin", nullable = true)
    private Timestamp  lastLogin = null; 
    @Column(name = "dateCreated", nullable = false)  
    private Timestamp  dateCreated = null;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "phone_id")
      private Set<Phone>  phones;
    /**
     * 
     * @return boolean validation of email
     */
    public boolean checkEmail() {
        String  regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }
    /**
     * 
     * @param password
     * @return 
     */
    public  boolean isValidPassword() {
        if (this.password == null || this.password.isEmpty()) {
            return false;
        }
        System.err.println("Password: " + this.password);
        String regex = "^(?=.*[A-Z]{2,2})(?=.*[0-9]{2,2})\\S{8,12}$"; 
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(this.password).matches();
    }
}
