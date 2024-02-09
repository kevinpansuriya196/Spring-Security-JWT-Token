package com.example.SecurityDemo.service;

import com.example.SecurityDemo.entity.UserInfo;
import com.example.SecurityDemo.repository.UserInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserInfoServiceTest {
    @InjectMocks
    private UserInfoService userInfoService;

    @Mock
    private UserInfoRepository repository;
    @Mock
    private PasswordEncoder encoder;

    /*    loadUserByUsername    */
    @Test
    void loadUserByUsernameTest() {
        UserInfo userInfo = new UserInfo("testUser", "password", "ROLE_USER");
        when(repository.findByName("testUser")).thenReturn(Optional.of(userInfo));
        UserDetails userDetails = userInfoService.loadUserByUsername("testUser");
        assertEquals(userInfo.getName(), userDetails.getUsername());
    }

    /*    loadUserByUsername -exception    */
    @Test
    void loadUserByUsernameExceptionTest() {
        when(repository.findByName(anyString())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userInfoService.loadUserByUsername("nonexistentUser"));
    }

    @Test
    void addUserTest() {
        UserInfo userInfo = new UserInfo("abc", "abc", "USER");
        Mockito.when(encoder.encode(userInfo.getPassword())).thenReturn("encodedPassword");
        Mockito.when(repository.save(Mockito.any(UserInfo.class))).thenReturn(userInfo);
        String result = userInfoService.addUser(userInfo);
        assertEquals("User Added Successfully", result);
        Mockito.verify(encoder).encode("abc");
        Mockito.verify(repository).save(userInfo);
    }

    /*  -------------------------------------  */

}