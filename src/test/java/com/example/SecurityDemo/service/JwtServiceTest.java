package com.example.SecurityDemo.service;

import io.jsonwebtoken.Claims;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class JwtServiceTest {
    @InjectMocks
    private JwtService jwtService;

    private static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    private void setSecretKey(String key) {
        ReflectionTestUtils.setField(jwtService, "secretKey", key);
    }

    /* generateToken - try block */
    @Test
    void generateTokenTest() {
        String userName = "test user";
        String token = jwtService.generateToken(userName);
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    /* generateToken - catch block */
    @Test
    void generateTokenExceptionTest() {
        String userName = null;
        String token = jwtService.generateToken(userName);
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(userName, extractedUsername);
    }


    @Test
    public void testGenerateToken_Exception() {
        JwtService jwtService = new JwtService();
        String userName = "testUser";
        JwtService spyJwtService = spy(jwtService);
        doThrow(new RuntimeException()).when(spyJwtService).createToken(anyMap(), eq(userName));
        String actualResult = spyJwtService.generateToken(userName);
        assertEquals("Invalid information", actualResult);
    }

    /*  extractUsername  */
    @Test
    void extractUsernameTest() {
        String userName = "test user";
        String token = jwtService.generateToken(userName);
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(userName, extractedUsername);
    }

    /*  extractExpiration */
    @Test
    void extractExpirationTest() {
        String userName = "test user";
        String token = jwtService.generateToken(userName);
        Date expirationDate = jwtService.extractExpiration(token);
        assertNotNull(expirationDate);
    }

    @Test
    void extractClaimTest() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("claim1", "value1");
        claims.put("claim2", 123);
        String userName = "testUser";
        String token = jwtService.createToken(claims, userName);
        Claims extractedClaims = jwtService.extractAllClaims(token);
        assertNotNull(extractedClaims);
        assertEquals("value1", extractedClaims.get("claim1", String.class));
        assertEquals(123, extractedClaims.get("claim2", Integer.class));
    }

    @Test
    void extractClaimTestException() {

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> jwtService.extractAllClaims("invalidToken")
        );
        assertEquals("Error parsing JWT token", exception.getMessage());
    }


    @Test
    void validateTokenTest() {
        String userName = "testUser";
        Collection<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails userDetails = new User(userName, "password", authorities);
        String token = jwtService.generateToken(userName);
        boolean isValid = jwtService.validateToken(token, userDetails);
        assertTrue(isValid);

    }

    /*   validateToken - invalid username  */
    @Test
    void validateToken() {
        String validUserName = "testUser";
        Collection<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails userDetails = new User(validUserName, "password", authorities);
        String token = jwtService.generateToken(validUserName);
        String invalidUserName = "invalidUser";
        UserDetails invalidUserDetails = new User(invalidUserName, "password", authorities);
        boolean isValid = jwtService.validateToken(token, invalidUserDetails);
        assertFalse(isValid);
    }


    @Test
    public void testValidateTokenNonExpiredTT() {
        JwtService jwtService = new JwtService();
        String username = "testUser";
        Collection<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetails userDetails = new User("testUser", "testUser", authorities);
        String token = jwtService.generateToken(username);
        Boolean isValid = jwtService.validateToken(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    public void testValidateTokenTF() {
        String username = "testUser";
        Collection<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetails userDetails = new User("testUser", "testUser", authorities);
        String ectualToken = jwtService.generateToken(username);

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjEiLCJpYXQiOjE3MD" +
                "cxMTM4MjksImV4cCI6MTcwNzExNDEyOX0.Sz3z52pcNPNcKVsvuM_4AyAZ2-oCKsKUTO05T6WwnoI";

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date date = calendar.getTime();

        Claims claims = mock(Claims.class);
        claims.setExpiration(date);


        Boolean response = jwtService.validateToken(ectualToken, userDetails);
    }

    /* - -------------------------------- - */
    @Before
    public void setUp() {
        jwtService = new JwtService();
    }

    @Test
    public void testValidateTokenReturnsTrue() {
        User userDetails = mock(User.class);
        when(userDetails.getUsername()).thenReturn("testUser");
        String token = jwtService.generateToken("testUser");
        assertTrue(jwtService.validateToken(token, userDetails));
    }


    @Test
    public void testValidateToken_InvalidUsername_ReturnsFalse() {
        User userDetails = mock(User.class);
        when(userDetails.getUsername()).thenReturn("testUser");
        String token = jwtService.generateToken("anotherUser");
        assertFalse(jwtService.validateToken(token, userDetails));
    }

    private String setTokenExpirationInPast(String token) {
        Date expirationDate = jwtService.extractExpiration(token);
        expirationDate.setTime(System.currentTimeMillis() - 60 * 1000);
        return jwtService.createToken(jwtService.extractAllClaims(token), jwtService.extractUsername(token));
    }
}