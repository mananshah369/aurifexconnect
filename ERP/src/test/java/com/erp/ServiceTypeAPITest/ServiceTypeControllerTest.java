package com.erp.ServiceTypeAPITest;

import com.erp.Dto.Request.ServiceTypeRequest;
import com.erp.Mapper.ServiceType.ServiceTypeMapper;
import com.erp.Repository.ServiceType.ServiceTypeRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ServiceTypeRepository repository;

    @Autowired
    private ServiceTypeMapper serviceTypeMapper;

    private static Long serviceId;

    @Test
    @Order(1)
    void TestAddService() throws Exception {
        ServiceTypeRequest request = new ServiceTypeRequest();
        request.setServiceName("web");
        request.setServicePrices(540.00);
        MvcResult result = mockMvc.perform(post("/service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        System.out.println(responseContent);
        JsonNode jsonResponse = objectMapper.readTree(responseContent);

        serviceId = jsonResponse.path("data").path("serviceId").asLong();
        assertNotNull(serviceId, "Service ID should not be null after creating service.");
        System.out.println("Service ID: " + serviceId);
    }

    @Test
    @Order(2)
    void testFindByServiceId() throws Exception {
        System.out.println("Service ID: " + serviceId);
        assertNotNull(serviceId, "Service ID should not be null before searching.");
        mockMvc.perform(get("/service/" + serviceId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.serviceName").value("web"))
                .andExpect(jsonPath("$.data.servicePrices").value(540.0));
    }

    @Test
    @Order(3)
    void testUpdateServiceById() throws Exception {
        ServiceTypeRequest updateRequest = new ServiceTypeRequest();
        updateRequest.setServiceName("app");
        updateRequest.setServicePrices(600);

        mockMvc.perform(put("/service/" + serviceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/service/" + serviceId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.serviceName").value("app"))
                .andExpect(jsonPath("$.data.servicePrices").value(600.0));
    }

    @Test
    @Order(4)
    void testFindServiceByName() throws Exception {
        // Find the service by it`s name
        mockMvc.perform(get("/service?name=app"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[*].serviceName",everyItem(is("app"))));
    }
    @Test
    @Order(5)
    void testDeleteServiceById() throws Exception {
        System.out.println("Service ID: " + serviceId);
        mockMvc.perform(delete("/service/" + serviceId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/service/" + serviceId))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(6)
    void cleanUp() {
        repository.deleteAll();
    }


}
