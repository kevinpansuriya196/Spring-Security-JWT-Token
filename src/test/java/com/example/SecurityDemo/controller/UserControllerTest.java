package com.example.SecurityDemo.controller;

import com.example.SecurityDemo.entity.AuthRequest;
import com.example.SecurityDemo.entity.UserInfo;
import com.example.SecurityDemo.repository.UserInfoRepository;
import com.example.SecurityDemo.service.JwtService;
import com.example.SecurityDemo.service.UserInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserControllerTest {

    @InjectMocks
    private UserController userController;
    @Mock
    private UserInfoService service;
    @Mock
    private UserInfoRepository user;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    /*   addNewUser   */
    @Test
    void addNewUserTest() {
        UserInfo userInfo = new UserInfo();
        String response = "User Added Successfully";
        Mockito.when(service.addUser(any())).thenReturn(response);
        String result = userController.addNewUser(userInfo);
        Assertions.assertEquals(result, response);
    }

    /*    userProfile     */
    @Test
    void userProfileTest() {
        List<UserInfo> list = new ArrayList<>();
        Mockito.when(user.findUsers()).thenReturn(list);
        List<UserInfo> response = userController.userProfile();
        Assertions.assertEquals(list, response);
    }

    /*    adminProfile    */
    @Test
    void adminProfileTest() {
        List<UserInfo> list = new ArrayList<>();
        Mockito.when(user.findAdmins()).thenReturn(list);
        List<UserInfo> response = userController.adminProfile();
        Assertions.assertEquals(list, response);
    }

    @Test
    void authenticateAndGetTokenTest() {
    }

    /*  -------------------------------- -*/
    @Test
    void authenticateAndGetToken_ValidUser_ReturnsToken() {
        AuthRequest authRequest = new AuthRequest("validUsername", "validPassword");
        Authentication mockAuthentication = mock(Authentication.class);
        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mockAuthentication);
        Mockito.when(mockAuthentication.isAuthenticated()).thenReturn(true);
        Mockito.when(jwtService.generateToken(authRequest.getUsername())).thenReturn("mockedToken");

        String result = userController.authenticateAndGetToken(authRequest);
        Mockito.verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        Mockito.verify(jwtService).generateToken(authRequest.getUsername());
        assertEquals("mockedToken", result);
    }

    @Test
    void authenticateAndGetToken_InvalidUser_ThrowsException() {
        AuthRequest authRequest = new AuthRequest("invalidUsername", "invalidPassword");
        Authentication mockAuthentication = mock(Authentication.class);

        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mockAuthentication);
        Mockito.when(mockAuthentication.isAuthenticated()).thenReturn(false);
        assertThrows(UsernameNotFoundException.class, () -> userController.authenticateAndGetToken(authRequest));
    }
}