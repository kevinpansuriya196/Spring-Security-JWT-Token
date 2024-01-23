package com.example.SecurityDemo.service;


import com.example.SecurityDemo.entity.UserInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

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
}
