package com.example.SecurityDemo.service;

import com.example.SecurityDemo.entity.UserInfo;
import com.example.SecurityDemo.repository.UserInfoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class UserInfoServiceTest {
    @Mock
    private UserInfoRepository repository;
    @Mock
    private PasswordEncoder encoder;
    @InjectMocks
    private UserInfoService userInfoService;

    @Test
    void loadUserByUsername_UserFound_ReturnsUserDetails() {
        UserInfo userInfo = new UserInfo("testUser", "password", "ROLE_USER");
        when(repository.findByName("testUser")).thenReturn(Optional.of(userInfo));
        UserDetails userDetails = userInfoService.loadUserByUsername("testUser");
        assertEquals(userInfo.getName(), userDetails.getUsername());
    }

    @Test
    void loadUserByUsername_UserNotFound_ThrowsUsernameNotFoundException() {
        when(repository.findByName(anyString())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userInfoService.loadUserByUsername("nonexistentUser"));
    }

    @Test
    void addUser_UserAddedSuccessfully_ReturnsSuccessMessage() {
        // Arrange
        UserInfo userInfo = new UserInfo("newUser", "password", "ROLE_USER");
        Mockito.when(encoder.encode("password")).thenReturn("encodedPassword");
        Mockito.when(repository.save(userInfo)).thenReturn(userInfo);

        // Act
        System.out.println("userInfoService: " + userInfoService);
        String result = userInfoService.addUser(userInfo);

        // Assert
        assertEquals("User Added Successfully", result);
    }

}