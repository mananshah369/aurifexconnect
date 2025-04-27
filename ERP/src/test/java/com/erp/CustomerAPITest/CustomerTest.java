package com.erp.CustomerAPITest;

import com.erp.Dto.Request.CustomerRequest;
import com.erp.Mapper.Customer.CustomerMapper;
import com.erp.Model.Customer;
import com.erp.Repository.Customer.CustomerRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@SpringBootTest
public class CustomerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static long customerId;

    @Test
    @Order(1)
    void testCreateCustomer() throws Exception{
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setName("Kalu Kaliya");
        customerRequest.setEmail("kalu@gmail.com");
        customerRequest.setPhone("9876543210");
        customerRequest.setAddress("Banglore,India");

        Customer customer = customerMapper.maptoCustomer(customerRequest);
        customerRepository.save(customer);
        customerId = customer.getCustomerId();
        mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    void testFindByCustomerId() throws Exception{
        mockMvc.perform(get("/customer/"+customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Kalu Kaliya"))
                .andExpect(jsonPath("$.data.email").value("kalu@gmail.com"))
                .andExpect(jsonPath("$.data.phone").value("9876543210"))
                .andExpect(jsonPath("$.data.address").value("Banglore,India"));
    }

    @Test
    @Order(3)
    void testUpdateCustomerById() throws Exception {
        CustomerRequest updatedRequest = new CustomerRequest();
        updatedRequest.setName("Vishal Updated");
        updatedRequest.setEmail("updated@gmail.com");
        updatedRequest.setPhone("9999999999");
        updatedRequest.setAddress("Updated Address");

        mockMvc.perform(put("/customer/" + customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRequest)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/customer/" + customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Vishal Updated"))
                .andExpect(jsonPath("$.data.email").value("updated@gmail.com"))
                .andExpect(jsonPath("$.data.phone").value("9999999999"))
                .andExpect(jsonPath("$.data.address").value("Updated Address"));
    }

    @Test
    @Order(4)
    void testGetAllCustomers() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @Order(5)
    void testDeleteCustomerById() throws Exception {
        mockMvc.perform(delete("/customer/" + customerId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/customer/" + customerId))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(6)
    void cleanUp() {
        customerRepository.deleteAll();
    }
}
