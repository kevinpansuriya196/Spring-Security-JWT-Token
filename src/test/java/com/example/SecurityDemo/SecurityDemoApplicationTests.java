package com.example.SecurityDemo;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SecurityDemoApplicationTests {



    public static void main(String[] args) {
        SpringApplication.run(SecurityDemoApplication.class, args);
    }

    @Test
    public void contextLoads() {
        SecurityDemoApplication.main(new String[] {});
        assertTrue(true);
    }
}
