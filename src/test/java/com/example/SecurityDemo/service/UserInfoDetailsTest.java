package com.example.SecurityDemo.service;


import com.example.SecurityDemo.entity.AuthRequest;
import com.example.SecurityDemo.entity.UserInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static javafx.beans.binding.Bindings.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

//@ExtendWith(MockitoExtension.class)
class UserInfoDetailsTest {

    @InjectMocks
    private UserInfoDetails userInfoDetails;
    @Mock
    private UserInfo userInfo;

    String name = "admin";
    String password = "admin";
    Collection<? extends GrantedAuthority> authorities = Collections.singletonList(() -> "USER");

    @Test
    public void testGetAuthorities() {
        // Create a UserInfo object with some roles
        UserInfo userInfo = new UserInfo();
        userInfo.setName("username");
        userInfo.setPassword("password");
        userInfo.setRoles("ROLE_USER,ROLE_ADMIN");

        // Create UserInfoDetails object using the UserInfo
        UserInfoDetails userInfoDetails = new UserInfoDetails(userInfo);

        // Expected authorities
        List<SimpleGrantedAuthority> expectedAuthorities = Arrays.asList(
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );

        // Get authorities from UserInfoDetails
        List<SimpleGrantedAuthority> authorities = userInfoDetails.getAuthorities().stream()
                .map(authority -> (SimpleGrantedAuthority) authority)
                .collect(Collectors.toList());

        // Assert that the authorities match the expected authorities
        assertEquals(expectedAuthorities, authorities);
    }

    @Test
    void getPasswordTest() {
        userInfoDetails = new UserInfoDetails(new UserInfo("admin", "admin", "USER"));
        String resultPassword = userInfoDetails.getPassword();
        assertEquals("admin", resultPassword);
    }


    @Test
    void isAccountNonExpiredTest() {
        UserInfoDetails userInfoDetails = new UserInfoDetails(new UserInfo("admin", "admin", "USER"));
        boolean result = userInfoDetails.isAccountNonExpired();
        assertTrue(result);
    }

    @Test
    void isAccountNonLockedTest() {
        userInfoDetails = new UserInfoDetails(new UserInfo("admin", "admin", "USER"));
        boolean result = userInfoDetails.isAccountNonLocked();
        assertTrue(result);
    }

    @Test
    void isCredentialsNonExpiredTest() {
        userInfoDetails = new UserInfoDetails(new UserInfo("admin", "admin", "USER"));
        boolean result = userInfoDetails.isCredentialsNonExpired();
        assertTrue(result);
    }

    @Test
    void isEnabledTest() {
        userInfoDetails = new UserInfoDetails(new UserInfo("1", "admin", "admin@gmail.com"));
        boolean result = userInfoDetails.isEnabled();
        assertTrue(result);
    }

    private class CustomUserDetails {
    }
}
