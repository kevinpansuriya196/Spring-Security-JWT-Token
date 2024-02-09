package com.example.SecurityDemo.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthRequestTest {
    @Test
    public void testAuthRequestConstructor() {
        String username = "testUser";
        String password = "testPassword";
        AuthRequest authRequest = new AuthRequest(username, password);
        assertEquals(username, authRequest.getUsername());
        assertEquals(password, authRequest.getPassword());
    }

    @Test
    public void testAuthRequestGettersAndSetters() {
        AuthRequest authRequest = new AuthRequest();
        String username = "testUser";
        String password = "testPassword";
        authRequest.setUsername(username);
        authRequest.setPassword(password);
        assertEquals(username, authRequest.getUsername());
        assertEquals(password, authRequest.getPassword());
    }
    @Test
    public void testEquals() {
        AuthRequest request1 = new AuthRequest("user1", "password1");
        AuthRequest request2 = new AuthRequest("user1", "password1");
        AuthRequest request3 = new AuthRequest("user2", "password2");

        assertEquals(request1, request2); // Should be equal since they have the same username and password
        assertNotEquals(request1, request3); // Should not be equal since they have different username and password
    }

    @Test
    public void testHashCode() {
        AuthRequest request1 = new AuthRequest("user1", "password1");
        AuthRequest request2 = new AuthRequest("user1", "password1");
        AuthRequest request3 = new AuthRequest("user2", "password2");

        assertEquals(request1.hashCode(), request2.hashCode()); // Hash codes should be equal for equal objects
        assertNotEquals(request1.hashCode(), request3.hashCode()); // Hash codes should not be equal for different objects
    }

    @Test
    public void testToString() {
        AuthRequest request = new AuthRequest("user1", "password1");

        assertEquals("AuthRequest(username=user1, password=password1)", request.toString());
    }


}