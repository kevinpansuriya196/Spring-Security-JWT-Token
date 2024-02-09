//package com.example.SecurityDemo.archived.config;
//
//import com.example.SecurityDemo.config.SecurityConfig;
//import com.example.SecurityDemo.controller.UserController;
//import com.example.SecurityDemo.filter.JwtAuthFilter;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@SpringBootTest
//class SecurityConfigTest {
//    @InjectMocks
//    private SecurityConfig securityConfig;
//    @Mock
//    private MockMvc mockMvc;
//    @MockBean
//    private JwtAuthFilter authFilter;
//
//    @Autowired
//    private UserController yourController;
//
//    @Autowired
//    private SecurityFilterChain securityFilterChain;
//
//
//    @Test
//    @WithMockUser(username = "user", roles = "USER")
//    void testSecurityFilterChain1_UserPath() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/auth/user/**"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    @Test
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    void testSecurityFilterChain1_AdminPath() throws Exception {
//        // Test that access to "/auth/admin/**" is permitted for authenticated admin
//        mockMvc.perform(MockMvcRequestBuilders.get("/auth/admin/adminProfile"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    @Test
//    void testSecurityFilterChain1_GenerateTokenPath() throws Exception {
//        // Test that access to "/auth/generateToken" is permitted without authentication
//        mockMvc.perform(MockMvcRequestBuilders.get("/auth/generateToken"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    // Add more tests as needed for different scenarios
//
//    private static final String AUTHORIZED_PATH = "/auth/authorized";
//    private static final String UNAUTHORIZED_PATH = "/auth/unauthorized";
//
//    @Test
//    void testSecurityFilterChain1_AuthorizedPath() throws Exception {
//        // Test that access to "/auth/authorized" is permitted for authenticated user
//        mockMvc.perform(MockMvcRequestBuilders.get(AUTHORIZED_PATH))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    @Test
//    void testSecurityFilterChain1_UnauthorizedPath() throws Exception {
//        // Test that access to "/auth/unauthorized" is denied for unauthenticated user
//        mockMvc.perform(MockMvcRequestBuilders.get(UNAUTHORIZED_PATH))
//                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
//    }
//
//
//}