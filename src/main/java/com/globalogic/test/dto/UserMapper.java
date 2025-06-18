package com.globalogic.test.dto;
import com.globalogic.test.entity.User;
import com.globalogic.test.entity.Phone;

/**
 * * UserMapper.java
 * * This class provides methods to convert between User and UserDto objects.
 */
public class UserMapper {
    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setToken(user.getToken());
        userDto.setIsActive(user.getIsActive());
        userDto.setLastLogin(user.getLastLogin());
        userDto.setDateCreated(user.getDateCreated());
        userDto.setPhones(user.getPhones());
        return userDto;
    }
    /**
     * toEntity
     * Converts a UserDto object to a User entity.
     * @param userDto
     * @return
     */
    public static User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setToken(userDto.getToken());
        user.setIsActive(userDto.getIsActive());
        user.setLastLogin(userDto.getLastLogin());
        user.setDateCreated(userDto.getDateCreated());
        user.setPhones(userDto.getPhones());
        return user;
    }

}
