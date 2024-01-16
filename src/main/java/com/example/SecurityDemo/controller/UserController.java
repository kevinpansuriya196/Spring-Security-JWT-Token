package com.example.SecurityDemo.controller;

import com.example.SecurityDemo.entity.AuthRequest;
import com.example.SecurityDemo.entity.UserInfo;
import com.example.SecurityDemo.repository.UserInfoRepository;
import com.example.SecurityDemo.service.JwtService;
import com.example.SecurityDemo.service.UserInfoService;
//import com.ey.springboot3security.entity.AuthRequest;
//import com.ey.springboot3security.entity.UserInfo;
//import com.ey.springboot3security.service.JwtService;
//import com.ey.springboot3security.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserInfoRepository User;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return service.addUser(userInfo);
    }

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('USER')")
    public List<UserInfo> userProfile() {
        return User.FindUsers();
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserInfo> adminProfile() {
        return User.FindAdmins();
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
}

