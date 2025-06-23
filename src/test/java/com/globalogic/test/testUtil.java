package com.globalogic.test;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import java.beans.Transient;
import com.globalogic.test.util.JwtUtil;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class testUtil {
    @InjectMocks
    private JwtUtil jwtUtil; 

    @Test
    public void testGenerateToken() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username);
        assertNotNull(token, "Token should not be null");   
    }

    @Test
    public void testValidateToken() {   
        String username = "testUser";
        String token = jwtUtil.generateToken(username);
        System.out.println("Generated Token: " + token);
        boolean isValid = jwtUtil.validateToken(token);
        assertTrue(isValid, "Token should be valid for the correct user");
    }

    @Test
    public void testGetUsernameFromToken() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username);
        String extractedUsername = jwtUtil.extractUsername(token);
        assertNotNull(extractedUsername, "Extracted username should not be null");
        assertEquals(extractedUsername, "testUser");
    }
}

