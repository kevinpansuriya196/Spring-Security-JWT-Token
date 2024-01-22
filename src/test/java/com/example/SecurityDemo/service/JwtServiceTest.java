package com.example.SecurityDemo.service;

import io.jsonwebtoken.Claims;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

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
        String userName = "";
        String token = jwtService.generateToken(userName);
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(userName, extractedUsername);
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

    /*   isTokenExpired - return false  */
    @Test
    void isTokenExpiredFalse() {
        String userName = "testUser";
        String token = jwtService.generateToken(userName);
        JwtService mockedJwtService = Mockito.spy(jwtService);
        Mockito.when(mockedJwtService.extractExpiration(token))
                .thenReturn(new Date(System.currentTimeMillis() + 1000 * 60 * 5));
        boolean isExpired = mockedJwtService.isTokenExpired(token);
        assertFalse(isExpired);
    }
    /*   isTokenExpired - return true  */

    @Test
    void isTokenExpiredTrue() {
        String userName = "testUser";
        String token = jwtService.generateToken(userName);
        JwtService mockedJwtService = Mockito.spy(jwtService);
        Mockito.when(mockedJwtService.extractExpiration(token))
                .thenReturn(new Date(System.currentTimeMillis() - 1000 * 60 * 5));
        boolean isExpired = mockedJwtService.isTokenExpired(token);
        assertTrue(isExpired);

    }

    /* validateToken */
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

}