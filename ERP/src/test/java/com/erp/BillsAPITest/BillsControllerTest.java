package com.erp.BillsAPITest;

import com.erp.Dto.Request.BillsRequest;
import com.erp.Dto.Request.ServiceTypeRequest;
import com.erp.Enum.AmountStatus;
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

import java.time.LocalDate;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BillsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static Long billId;

    @Test
    @Order(1)
    public void testAddBill() throws Exception {
        BillsRequest billsRequest = new BillsRequest();
        billsRequest.setTranDate(LocalDate.of(2025, 4, 17));
        billsRequest.setDueDate(LocalDate.of(2025, 4, 30));
        billsRequest.setDescription("Office Chair Purchase");
        billsRequest.setReferenceBillNo("BILL-2025-0417");
        billsRequest.setTotalAmount(15000.75);
        billsRequest.setStatus(AmountStatus.UNPAID);


        MvcResult result = mockMvc.perform(post("/bills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(billsRequest)))
                .andExpect(status().isCreated())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        System.out.println(responseContent);
        JsonNode jsonResponse = objectMapper.readTree(responseContent);

        billId = jsonResponse.path("data").path("billId").asLong();
        assertNotNull(billId, "supplier ID should not be null after creating service.");
        System.out.println("supplier ID: " + billId);
    }

    @Test
    @Order(2)
    void testFindByBillId() throws Exception {
        System.out.println("Service ID: " + billId);
        assertNotNull(billId, "Service ID should not be null before searching.");
        mockMvc.perform(get("/service/" + billId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.tranDate").value("web"))
                .andExpect(jsonPath("$.data.dueDate").value(540.0))
                .andExpect(jsonPath("$.data.description").value(540.0))
                .andExpect(jsonPath("$.data.referenceBillNo").value(540.0))
                .andExpect(jsonPath("$.data.totalAmount").value(540.0))
                .andExpect(jsonPath("$.data.status").value(540.0))
                .andExpect(jsonPath("$.data.dueDate").value(540.0))
                .andExpect(jsonPath("$.data.dueDate").value(540.0));
    }

    @Test
    @Order(3)
    void testUpdateServiceById() throws Exception {
        ServiceTypeRequest updateRequest = new ServiceTypeRequest();
        updateRequest.setServiceName("app");
        updateRequest.setServicePrices(600);

        mockMvc.perform(put("/service/" + billId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/service/" + billId))
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
        System.out.println("Service ID: " + billId);
        mockMvc.perform(delete("/service/" + billId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/service/" + billId))
                .andExpect(status().isNotFound());
    }
}
