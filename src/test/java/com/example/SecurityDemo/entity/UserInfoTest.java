package com.example.SecurityDemo.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInfoTest {


    @Test
    public void testUserInfoConstructor() {
        UserInfo userInfo = new UserInfo("John", "password123", "ROLE_USER");

        assertEquals("John", userInfo.getName());
        assertEquals("password123", userInfo.getPassword());
        assertEquals("ROLE_USER", userInfo.getRoles());
    }

    @Test
    public void testUserInfoGettersAndSetters() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setName("Alice");
        userInfo.setPassword("secret");
        userInfo.setRoles("ROLE_ADMIN");

        assertEquals(1, userInfo.getId());
        assertEquals("Alice", userInfo.getName());
        assertEquals("secret", userInfo.getPassword());
        assertEquals("ROLE_ADMIN", userInfo.getRoles());
    }


    @Test
    public void testEqualsAndHashCode() {
        UserInfo userInfo1 = new UserInfo(1, "John", "john@example.com", "password123", "ROLE_USER");
        UserInfo userInfo2 = new UserInfo(1, "John", "john@example.com", "password123", "ROLE_USER");
        UserInfo userInfo3 = new UserInfo(2, "Alice", "alice@example.com", "password456", "ROLE_ADMIN");

        // Test equals
        assertTrue(userInfo1.equals(userInfo2));
        assertFalse(userInfo1.equals(userInfo3));

        // Test hashCode
        assertEquals(userInfo1.hashCode(), userInfo2.hashCode());
        assertNotEquals(userInfo1.hashCode(), userInfo3.hashCode());
    }

    @Test
    public void testToString() {
        UserInfo userInfo = new UserInfo(1, "John", "john@example.com", "password123", "ROLE_USER");
        String expectedToString = "UserInfo(id=1, name=John, email=john@example.com, password=password123, roles=ROLE_USER)";
        assertEquals(expectedToString, userInfo.toString());
    }

    @Test
    public void testUserInfoConstructorWithAllFields() {
        UserInfo userInfo = new UserInfo(1, "John", "john@example.com", "password123", "ROLE_USER");

        assertEquals(1, userInfo.getId());
        assertEquals("John", userInfo.getName());
        assertEquals("john@example.com", userInfo.getEmail());
        assertEquals("password123", userInfo.getPassword());
        assertEquals("ROLE_USER", userInfo.getRoles());
    }

    @Test
    public void testSetAndGetEmail() {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("john@example.com");

        assertEquals("john@example.com", userInfo.getEmail());
    }


}