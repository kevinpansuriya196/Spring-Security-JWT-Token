package com.example.SecurityDemo.filter;

import com.example.SecurityDemo.service.JwtService;
import com.example.SecurityDemo.service.UserInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthFilterTest {
    @Mock
    private JwtService jwtService;

    @Mock
    private UserInfoService userInfoService;

    @InjectMocks
    private JwtAuthFilter jwtAuthFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testHandleJwtException() throws IOException {
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        try {
            when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        } catch (IOException e) {
            fail("Exception occurred while stubbing getWriter()");
        }
        try {
            jwtAuthFilter.handleJwtException(response, "admin", new RuntimeException("exception"));
        } catch (IOException e) {
            fail("Exception occurred while handling JWT exception: " + e.getMessage());
        }
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).getWriter();
        String writtenValue = stringWriter.toString();
        assertTrue(writtenValue.contains("admin"), "Expected message not found in the response writer content");
    }

    @Test
    public void testValidJwtTokenTT() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        UserDetails userDetails = User.withUsername("testuser").password("password").roles("USER").build();
        when(request.getHeader("Authorization")).thenReturn("Bearer validJwtToken");
        when(jwtService.extractUsername("validJwtToken")).thenReturn("testuser");
        when(userInfoService.loadUserByUsername("testuser")).thenReturn(userDetails);
        when(jwtService.validateToken("validJwtToken", userDetails)).thenReturn(true);
        jwtAuthFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
        verifyNoMoreInteractions(response);
    }


    @Test
    public void testValidJwtTokenFF() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        UserDetails userDetails = User.withUsername("testuser").password("password").roles("USER").build();
        when(request.getHeader("Authorization")).thenReturn("validJwtToken");
        jwtAuthFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
        verifyNoMoreInteractions(response);
    }

    @Test
    public void testValidJwtToken2FT() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        UserDetails userDetails = User.withUsername("testuser").password("password").roles("USER").build();

        when(request.getHeader("Authorization")).thenReturn("Bearer validJwtToken");
        when(jwtService.extractUsername("validJwtToken")).thenReturn(null);
        jwtAuthFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
        verifyNoMoreInteractions(response);
    }


    @Test
    public void testValidJwtToken2TF() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        SecurityContextHolder holder = mock(SecurityContextHolder.class);

        UserDetails userDetails = User.withUsername("testuser").password("password").roles("USER").build();

        when(request.getHeader("Authorization")).thenReturn("Bearer validJwtToken");
        when(jwtService.extractUsername("validJwtToken")).thenReturn(null);
        jwtAuthFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
        verifyNoMoreInteractions(response);
    }

    @Test
    public void testValidJwtToken3F() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer validJwtToken");
        when(jwtService.extractUsername("validJwtToken")).thenReturn("testuser");
        jwtAuthFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
        verifyNoMoreInteractions(response);
    }

    @Test
    public void testMissingJwtToken() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        when(request.getHeader("Authorization")).thenReturn(null);
        jwtAuthFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
        verifyNoMoreInteractions(response);
    }
}
