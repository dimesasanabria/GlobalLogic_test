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
import com.globalogic.test.util.JwtUtil;
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
import com.globalogic.test.util.JwtUtil;
import org.mockito.Mock;
import io.jsonwebtoken.ExpiredJwtException;

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
    @Mock
    private JwtUtil jwtUtil;
    

    
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
     //   when(userService.saveService(dtoUser))
     //   .thenThrow(new ExceptionFormatEmail("The format Email is invalid"));
        ResponseEntity<Object> response = userServicesIn.saveService(dtoUser);
        System.out.println("ResponseBadRequest: " + response);
        Object result = response.getBody();
            // Validate
        assertNotNull(result);
        assertTrue(result instanceof ExceptionList);
        ExceptionList resultDto = (ExceptionList) result;
        assertEquals("The format Email is invalid", resultDto.getErrors().get(0).getDetail());
    }

    @Test
    public void testExceptionGenerateToken() {
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
        System.out.println("ResponseBadRequest: " + response);
        Object result = response.getBody();
            // Validate
        assertNotNull(result);
        assertTrue(result instanceof ExceptionList);
        ExceptionList resultDto = (ExceptionList) result;
        assertEquals("Error generating token", resultDto.getErrors().get(0).getDetail());
    }
    @Test
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
    @Test
     public void testExceptionInvalidToken() {
        UserDto dtoUser = new UserDto();
        Authentication authentication = mock(Authentication.class);
        //when(authentication.getCredentials()).thenReturn("");        
        // Execute
        String token = (String) authentication.getCredentials();
        ResponseEntity<Object> result = userServicesIn.getUser(token);
        assertNotNull(result);
        assertTrue(result.getBody() instanceof ExceptionList);
        ExceptionList resultDto = (ExceptionList) result.getBody();
        assertEquals("Unauthorized: No token provided or invalid token. Please provide a valid token in the Authorization header.", resultDto.getErrors().get(0).getDetail());
    }

      @Test
    public void ExceptionInvalidToken() {
        UserDto dtoUser = new UserDto();
        dtoUser.setName("Diego M");
        dtoUser.setEmail("codigo726778@gmail.com");
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
        Authentication authentication = mock(Authentication.class);
        // Mock the repository
        when(authentication.getCredentials()).thenReturn("Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb2RpZ283MjY3OTMzMTIyQGdtYWlsLmNvbSIsImV4cCI6MTc1MDE2NDU5NCwiaWF0IjoxNzUwMTYzNjk0fQ.ksiD70BrWFeKNplhKSTi2di5n37_GxYpfZfJ_ngTDYk");        
        // Execute
        String token = (String) authentication.getCredentials();
        when(userRepository.findUserByEmail(savedUser.getEmail())).thenReturn(Optional.of(savedUser));
        ResponseEntity<Object> result = userServicesIn.getUser(token);
        assertNotNull(result);
        assertTrue(result.getBody() instanceof ExceptionList);
        ExceptionList resultDto = (ExceptionList) result.getBody();
        assertEquals("Token is invalid", resultDto.getErrors().get(0).getDetail());
    }

    @Test
    public void ExceptionTokenExtractEmail() {
        UserDto dtoUser = new UserDto();
        dtoUser.setName("Diego M");
        dtoUser.setEmail("codigoww26778@gmail.com");
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
        Authentication authentication = mock(Authentication.class);
            // Mock the repository
        when(authentication.getCredentials()).thenReturn("Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb2RpZ283MjY3OTMzMTIyQGdtYWlsLmNvbSIsImV4cCI6MTc1MDE2NDU5NCwiaWF0IjoxNzUwMTYzNjk0fQ.ksiD70BrWFeKNplhKSTi2di5n37_GxYpfZfJ_ngTDYk");        
            // Execute
        String token = (String) authentication.getCredentials();
            //when(userRepository.findUserByEmail(savedUser.getEmail())).thenReturn(Optional.of(savedUser));
        when(jwtUtil.validateToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb2RpZ283MjY3OTMzMTIyQGdtYWlsLmNvbSIsImV4cCI6MTc1MDE2NDU5NCwiaWF0IjoxNzUwMTYzNjk0fQ.ksiD70BrWFeKNplhKSTi2di5n37_GxYpfZfJ_ngTDYk")).thenReturn(true);        
        when(jwtUtil.extractUsername("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb2RpZ283MjY3OTMzMTIyQGdtYWlsLmNvbSIsImV4cCI6MTc1MDE2NDU5NCwiaWF0IjoxNzUwMTYzNjk0fQ.ksiD70BrWFeKNplhKSTi2di5n37_GxYpfZfJ_ngTDYk")).thenReturn(savedUser.getEmail());
        when(jwtUtil.generateToken(savedUser.getEmail())).thenReturn("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb2RpZ283MjY3OTMzMTIyQGdtYWlsLmNvbSIsImV4cCI6MTc1MDE2NDU5NCwiaWF0IjoxNzUwMTYzNjk0fQ.ksiD70BrWFeKNplhKSTi2di5n37_GxYpfZfJ_ngTDYk");
        ResponseEntity<Object> result = userServicesIn.getUser(token);
        assertNotNull(result);
        assertTrue(result.getBody() instanceof ExceptionList);
        ExceptionList resultDto = (ExceptionList) result.getBody();
        assertEquals("User not found with email: codigoww26778@gmail.com", resultDto.getErrors().get(0).getDetail());
    }

    @Test
    public void ExceptionTestgetUser() {
        UserDto dtoUser = new UserDto();
        dtoUser.setName("Diego M");
        dtoUser.setEmail("codigo726778@gmail.com");
        dtoUser.setPassword("Password123");
        Authentication authentication = mock(Authentication.class);
        // Mock the repository
        when(authentication.getCredentials()).thenReturn("Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb2RpZ283MjY3OTMzMTIyQGdtYWlsLmNvbSIsImV4cCI6MTc1MDE2NDU5NCwiaWF0IjoxNzUwMTYzNjk0fQ.ksiD70BrWFeKNplhKSTi2di5n37_GxYpfZfJ_ngTDYk");        
        // Execute
        String token = (String) authentication.getCredentials();
        when(!jwtUtil.validateToken(token))
        .thenThrow(new ExpiredJwtException(null, null, "Token is invalid"));    
        ResponseEntity<Object> result = userServicesIn.getUser(token);
        System.out.println("result: " + result); 
        assertNotNull(result);
        assertTrue(result.getBody() instanceof ExceptionList);
        ExceptionList resultDto = (ExceptionList) result.getBody();
        assertEquals("Token is invalid", resultDto.getErrors().get(0).getDetail());
    }        

}