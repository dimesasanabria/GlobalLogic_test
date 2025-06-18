package com.globalogic.test.dto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Set;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.globalogic.test.entity.Phone;
import com.globalogic.test.entity.User;

public class UserDto {
    private Long id;
    private String name; 
    private String email;
    private String password;
    private String token;
    private Boolean isActive;
    private Timestamp  lastLogin = null; 
    private Timestamp  dateCreated = null;
    private Set<Phone>  phones;

    public UserDto() {
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    public Timestamp getLastLogin() {
        return lastLogin;
    }
    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    

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
         char tempArray[] = this.password.toCharArray();
         Arrays.sort(tempArray);
         String pass = new String(tempArray);
        String regex = "^(?=.*[A-Z])(?=.*\\d.*\\d)[A-Za-z\\d]{8,12}$"; // At least one uppercase letter, two digits, and 8-12 characters
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(pass).matches();
    }
   

}