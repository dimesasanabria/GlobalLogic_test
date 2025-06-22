package com.globalogic.test;

import com.globalogic.test.service.UserServiceImp;
import com.globalogic.test.entity.User;
import com.globalogic.test.service.UserService;
import com.globalogic.test.service.UserServiceImp;
import com.globalogic.test.persistence.UserRepository;
import com.globalogic.test.TestPersistence;
import com.globalogic.test.dto.UserDto;
import com.globalogic.test.dto.UserMapper;
import org.mockito.InjectMocks;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Optional;
import java.sql.Timestamp;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.Mock;
import com.globalogic.test.util.JwtUtil;
@SpringBootTest
public class TestService {    
    
    @InjectMocks
    @Autowired
    private UserServiceImp userServicesIn;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;
    
    
    @BeforeEach
    void setUp() {
        // Initialize mocks and inject them
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testgetUser() {
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
        when(jwtUtil.generateToken(savedUser.getEmail())).thenReturn("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb2RpZ283MjY3OTMzMTIyQGdtYWlsLmNvbSIsImV4cCI6MTc1MDE2NDU5NCwiaWF0IjoxNzUwMTYzNjk0fQ.ksiD70BrWFeKNplhKSTi2di5n37_GxYpfZfJ_ngTDYk");
        when(userRepository.findUserByEmail(dtoUser.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(savedUser)).thenReturn(savedUser);
        ResponseEntity<Object> uss = userServicesIn.saveService(dtoUser);
        System.out.println("Response: " + uss);
        Authentication authentication = mock(Authentication.class);
        
        // Mock the repository
        when(authentication.getCredentials()).thenReturn("Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb2RpZ283MjY3OTMzMTIyQGdtYWlsLmNvbSIsImV4cCI6MTc1MDE2NDU5NCwiaWF0IjoxNzUwMTYzNjk0fQ.ksiD70BrWFeKNplhKSTi2di5n37_GxYpfZfJ_ngTDYk");        
        // Execute
        String token = (String) authentication.getCredentials();
        when(userRepository.findUserByEmail(savedUser.getEmail())).thenReturn(Optional.of(savedUser));
        when(userRepository.save(savedUser)).thenReturn(savedUser);
        when(jwtUtil.validateToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb2RpZ283MjY3OTMzMTIyQGdtYWlsLmNvbSIsImV4cCI6MTc1MDE2NDU5NCwiaWF0IjoxNzUwMTYzNjk0fQ.ksiD70BrWFeKNplhKSTi2di5n37_GxYpfZfJ_ngTDYk")).thenReturn(true);        
        when(jwtUtil.extractUsername("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb2RpZ283MjY3OTMzMTIyQGdtYWlsLmNvbSIsImV4cCI6MTc1MDE2NDU5NCwiaWF0IjoxNzUwMTYzNjk0fQ.ksiD70BrWFeKNplhKSTi2di5n37_GxYpfZfJ_ngTDYk")).thenReturn(savedUser.getEmail());
        when(jwtUtil.generateToken(savedUser.getEmail())).thenReturn("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb2RpZ283MjY3OTMzMTIyQGdtYWlsLmNvbSIsImV4cCI6MTc1MDE2NDU5NCwiaWF0IjoxNzUwMTYzNjk0fQ.ksiD70BrWFeKNplhKSTi2di5n37_GxYpfZfJ_ngTDYk");
        ResponseEntity<Object> result = userServicesIn.getUser(token);
        System.out.println("result: " + result);    
        assertNotNull(result);
    }
}
