package com.globalogic.test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import com.globalogic.test.entity.User;
import java.sql.Timestamp;
import java.util.Optional;
import com.globalogic.test.exception.ExceptionDataNotFound;
import com.globalogic.test.persistence.UserRepository;
import com.globalogic.test.service.UserService;
import com.globalogic.test.service.UserServiceImp;
import com.globalogic.test.dto.UserDto;
import com.globalogic.test.dto.UserMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase 
public class TestPersistence {
    @Autowired
    private UserRepository userRepositoryGet;
        
        @Test
        public void testUserByEmail() {
          User userTest = new  User();
          userTest.setName("Diego M");
          userTest.setEmail("codigo7267911@gmail.com");
          userTest.setPassword("Password123");
          userTest.setToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb2RpZ283MjY3OTMzMTIyQGdtYWlsLmNvbSIsImV4cCI6MTc1MDE2NDU5NCwiaWF0IjoxNzUwMTYzNjk0fQ.ksiD70BrWFeKNplhKSTi2di5n37_GxYpfZfJ_ngTDYk");
          userTest.setIsActive(true);
          userTest.setDateCreated(new Timestamp(System.currentTimeMillis()));
          userRepositoryGet.save(userTest);
          Optional<User> result = userRepositoryGet.findUserByEmail(userTest.getEmail());
          assertTrue(result.isPresent());
        }
        
        @Test
        public void testSaveUser() {
          User userTest = new  User();
          userTest.setName("Diego M");
          userTest.setEmail("codigo7267911887778@gmail.com");
          userTest.setPassword("Password123");
          userTest.setToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb2RpZ283MjY3OTMzMTIyQGdtYWlsLmNvbSIsImV4cCI6MTc1MDE2NDU5NCwiaWF0IjoxNzUwMTYzNjk0fQ.ksiD70BrWFeKNplhKSTi2di5n37_GxYpfZfJ_ngTDYk");
          userTest.setIsActive(true);
          userTest.setDateCreated(new Timestamp(System.currentTimeMillis()));
          User result = userRepositoryGet.save(userTest);
          assertTrue(result.getIsActive());
        }
        
        
}
