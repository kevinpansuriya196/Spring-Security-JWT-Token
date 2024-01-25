package com.example.SecurityDemo.filter;

import com.example.SecurityDemo.service.JwtService;
import com.example.SecurityDemo.service.UserInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthFilterTest {
    @Mock
    private JwtService jwtService;

    @Mock
    private UserInfoService userDetailsService;

    @InjectMocks
    private JwtAuthFilter jwtAuthFilter;


    @Test
    void testDoFilterInternal() throws ServletException, IOException {
        // Mocking HttpServletRequest, HttpServletResponse, UserDetails, etc.
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        UserDetails userDetails = mock(UserDetails.class);

        // Mocking behavior for your dependencies
        when(request.getHeader("Authorization")).thenReturn("Bearer mockToken");
        when(jwtService.extractUsername("mockToken")).thenReturn("mockUsername");
        when(userDetailsService.loadUserByUsername("mockUsername")).thenReturn(userDetails);
        when(jwtService.validateToken("mockToken", userDetails)).thenReturn(true);

        // Calling the filter method
        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        // Verifying that the authentication was set on SecurityContextHolder
        verify(filterChain).doFilter(request, response);
        verify(userDetails).getAuthorities();
        verify(jwtService).validateToken("mockToken", userDetails);    }



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
        verify(response).getWriter(); // Verifies that getWriter() is called
        String writtenValue = stringWriter.toString();
        assertTrue(writtenValue.contains("admin"), "Expected message not found in the response writer content");
    }

}
