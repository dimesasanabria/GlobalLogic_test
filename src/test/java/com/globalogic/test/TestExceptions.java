package com.globalogic.test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.globalogic.test.entity.User;
import java.sql.Timestamp;
import java.util.Optional;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import com.globalogic.test.exception.ExceptionDataNotFound;
import com.globalogic.test.persistence.UserRepository;
import com.globalogic.test.service.UserService;
import com.globalogic.test.service.UserServiceImp;
import com.globalogic.test.dto.UserDto;
import com.globalogic.test.dto.UserMapper;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import com.globalogic.test.exception.ExceptionUserAlreadyExists;
import com.globalogic.test.exception.ExceptionFormatEmail;
import com.globalogic.test.exception.ExceptionList;
import com.globalogic.test.exception.ExceptionInvalidPassword;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc 
public class TestExceptions {    
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserServiceImp userService;
    @InjectMocks
    private UserServiceImp userServicesIn;
 
    @MockBean
    private UserRepository userRepository;
    
     @BeforeEach
    void setUp() {
        // Initialize mocks and inject them
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExceptionUserAlreadyExist() {
        UserDto dtoUser = new UserDto();
        dtoUser.setName("Diego M");
        dtoUser.setEmail("codigo7267911@gmail.com");
        dtoUser.setPassword("Password123");
        dtoUser.setToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb2RpZ283MjY3OTMzMTIyQGdtYWlsLmNvbSIsImV4cCI6MTc1MDE2NDU5NCwiaWF0IjoxNzUwMTYzNjk0fQ.ksiD70BrWFeKNplhKSTi2di5n37_GxYpfZfJ_ngTDYk");
        dtoUser.setIsActive(true);
        dtoUser.setDateCreated(new Timestamp(System.currentTimeMillis()));
        System.out.println("DTO: " + dtoUser);
        when(userService.saveService(dtoUser))
        .thenThrow(new ExceptionFormatEmail("This user already exists"));
    }
    @Test
    public void testExceptionFormatEmail() {
        UserDto dtoUser = new UserDto();
        dtoUser.setName("Diego M");
        dtoUser.setEmail("codigo72679gmail.com");
        dtoUser.setPassword("Password123");
        dtoUser.setToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb2RpZ283MjY3OTMzMTIyQGdtYWlsLmNvbSIsImV4cCI6MTc1MDE2NDU5NCwiaWF0IjoxNzUwMTYzNjk0fQ.ksiD70BrWFeKNplhKSTi2di5n37_GxYpfZfJ_ngTDYk");
        dtoUser.setIsActive(true);
        dtoUser.setDateCreated(new Timestamp(System.currentTimeMillis()));
        System.out.println("DTO: " + dtoUser);
        when(userService.saveService(dtoUser))
        .thenThrow(new ExceptionFormatEmail("The format Email is invalid"));
    }

    @Test
    public void testExceptionBadRequest() {
        UserDto dtoUser = new UserDto();
        dtoUser.setName("Diego M");
        dtoUser.setEmail("codigo7267911@gmail.com");
        dtoUser.setPassword("Password123");
        dtoUser.setIsActive(true);
        dtoUser.setDateCreated(new Timestamp(System.currentTimeMillis()));
        System.out.println("DTO: " + dtoUser);
        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName(dtoUser.getName());
        savedUser.setEmail(dtoUser.getEmail());
        savedUser.setPassword(dtoUser.getPassword());
        savedUser.setIsActive(dtoUser.getIsActive());
        savedUser.setDateCreated(dtoUser.getDateCreated());
            // Mock the repository
       when(userRepository.save(savedUser)).thenReturn(savedUser);
            // Execute
        ResponseEntity<Object> response = userServicesIn.saveService(dtoUser);
        Object result = response.getBody();
     
            // Validate
        assertNotNull(result);
        assertTrue(result instanceof ExceptionList);
        ExceptionList resultDto = (ExceptionList) result;
        assertEquals("Parameters incorrects in the request", resultDto.getErrors().get(0).getDetail());
    }

    public void testExceptionInvalidPassword() {
        UserDto dtoUser = new UserDto();
        dtoUser.setName("Diego M");
        dtoUser.setEmail("codigo72679@gmail.com");
        dtoUser.setPassword("password123");
        dtoUser.setToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb2RpZ283MjY3OTMzMTIyQGdtYWlsLmNvbSIsImV4cCI6MTc1MDE2NDU5NCwiaWF0IjoxNzUwMTYzNjk0fQ.ksiD70BrWFeKNplhKSTi2di5n37_GxYpfZfJ_ngTDYk");
        dtoUser.setIsActive(true);
        dtoUser.setDateCreated(new Timestamp(System.currentTimeMillis()));
        when(userService.saveService(dtoUser))
        .thenThrow(new ExceptionInvalidPassword("Password must contain between 8 and 12 characters, only one uppercase letter, and  two numbers in any position"));
    }

     public void testExceptionInvalidToken() {
        UserDto dtoUser = new UserDto();
        Authentication authentication = mock(Authentication.class);
        when(authentication.getCredentials()).thenReturn("Bearer ");        
        // Execute
        String token = (String) authentication.getCredentials();
        when(userService.getUser(token))
        .thenThrow(new ExceptionInvalidPassword("Unauthorized: No token provided or invalid token. Please provide a valid token in the Authorization header."));
    }

}