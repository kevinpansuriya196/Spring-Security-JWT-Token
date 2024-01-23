package com.example.SecurityDemo.config;

import com.example.SecurityDemo.controller.UserController;
import com.example.SecurityDemo.filter.JwtAuthFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecurityConfigTest {
    @InjectMocks
    private SecurityConfig securityConfig;
    @Mock
    private MockMvc mockMvc;
    @MockBean
    private JwtAuthFilter authFilter;

    @Autowired
    private UserController yourController;

    @Test
    void shouldAllowAccessToGenerateTokenEndpoint() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(yourController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/auth/generateToken"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldAllowAccessToUserEndpoint() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(yourController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/auth/user/somePath"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldAllowAccessToAdminEndpoint() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(yourController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/auth/admin/somePath"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldDenyAccessToAdminEndpointForUserWithoutAdminRole() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(yourController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/auth/admin/somePath"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

}