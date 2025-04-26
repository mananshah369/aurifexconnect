package com.erp.UserAPiTest;

import com.erp.Dto.Request.UserRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static com.erp.Enum.Role.ADMIN;
import static com.erp.Enum.Role.USER;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static Long userId;

    @Test
    @Order(1)
    public void testCreateUser() throws Exception {

        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("Balu");
        userRequest.setEmail("balu@gmail.com");
        userRequest.setPassword("Raja@12345");
        userRequest.setRole(USER);

        MvcResult result = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        System.out.println(responseContent);
        JsonNode jsonResponse = objectMapper.readTree(responseContent);

        userId = jsonResponse.path("data").path("id").asLong();
        assertNotNull(userId, "user ID should not be null after creating user.");
        System.out.println("user ID: " + userId);
    }

    @Test
    @Order(2)
    public void testFindUserById() throws Exception{

        mockMvc.perform(get("/api/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("Balu"))
                .andExpect(jsonPath("$.data.email").value("balu@gmail.com"))
                .andExpect(jsonPath("$.data.role").value(USER.name()));
        System.out.println("user ID: " + userId);
    }

    @Test
    @Order(3)
    public void testUpdateUserById() throws Exception{
        UserRequest updateUser = new UserRequest();
        updateUser.setUsername("Pm");
        updateUser.setEmail("pm@gmail.com");
        updateUser.setRole(ADMIN);

        mockMvc.perform(put("/api/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUser)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("Pm"))
                .andExpect(jsonPath("$.data.email").value("pm@gmail.com"))
                .andExpect(jsonPath("$.data.role").value(ADMIN.name()));
    }

    @Test
    @Order(4)
    public void testGetAllUser() throws Exception{
        MvcResult result =   mockMvc.perform(get("/api/users" ))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println(responseContent);
    }

    @Test
    @Order(5)
    public void testDeleteUserById() throws Exception{
        System.out.println("User ID: " + userId);
        mockMvc.perform(delete("/api/users/" + userId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/users/" + userId))
                .andExpect(status().isNotFound());
    }
}