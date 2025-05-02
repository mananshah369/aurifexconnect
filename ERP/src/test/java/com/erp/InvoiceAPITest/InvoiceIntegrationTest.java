//package com.erp.InvoiceAPITest;
//
//import com.erp.Dto.Request.InvoiceRequest;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDate;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class InvoiceIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    static long invoiceId;
//    static long testLedgerId = 1; // Replace with valid ledger ID or create via setup if needed
//
//    @Test
//    @Order(1)
//    public void testCreateInvoice() throws Exception {
//        InvoiceRequest invoiceRequest = new InvoiceRequest();
//        invoiceRequest.setDescription("Laptop Purchase");
//
//        MvcResult result = mockMvc.perform(post("/invoice?ledgerId=" + testLedgerId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invoiceRequest)))
//                .andExpect(status().isCreated())
//                .andReturn();
//
//    }
//
//    @Test
//    @Order(2)
//    void testFindInvoiceById() throws Exception {
//        assertNotEquals(0, invoiceId);
//        mockMvc.perform(get("/invoice/" + invoiceId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(invoiceId))
//                .andExpect(jsonPath("$.invoiceTitle").value("Test Invoice"))
//                .andExpect(jsonPath("$.description").value("Laptop Purchase"));
//    }
//
//    @Test
//    @Order(3)
//    void testUpdateInvoiceById() throws Exception {
//        InvoiceRequest updateRequest = new InvoiceRequest();
//        updateRequest.setInvoiceTitle("Updated Invoice Title");
//        updateRequest.setDescription("Updated Description");
//        updateRequest.setDueDate(LocalDate.of(2025, 6, 1));
//        updateRequest.setInvoiceType("PURCHASE");
//
//        mockMvc.perform(put("/invoice/" + invoiceId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updateRequest)))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(get("/invoice/" + invoiceId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.invoiceTitle").value("Updated Invoice Title"))
//                .andExpect(jsonPath("$.description").value("Updated Description"));
//    }
//
//    @Test
//    @Order(4)
//    void testDeleteInvoiceById() throws Exception {
//        mockMvc.perform(delete("/invoice/" + invoiceId))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(get("/invoice/" + invoiceId))
//                .andExpect(status().isNotFound());
//    }
//}
