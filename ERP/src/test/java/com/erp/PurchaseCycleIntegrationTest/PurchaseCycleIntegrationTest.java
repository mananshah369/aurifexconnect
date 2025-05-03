package com.erp.PurchaseCycleIntegrationTest;

import com.erp.Dto.Request.BillLineRequest;
import com.erp.Dto.Request.BillsRequest;
import com.erp.Dto.Request.LedgerRequest;
import com.erp.Enum.AmountStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PurchaseCycleIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(PurchaseCycleIntegrationTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static Long ledgerId;
    private static Long billId;
    private static Long billLineId1;
    private static Long billLineId2;

    private static final String ITEM_ID = "2";
    private static final String BILL_DESCRIPTION = "Office Chair Purchase";

    @Test
    @Order(1)
    @DisplayName("1. Create a new Ledger")
    void createLedger() throws Exception {
        LedgerRequest request = new LedgerRequest();
        request.setName("John Doe");
        request.setEmail("john.doe@example.com");
        request.setPhone("9876543210");
        request.setAddress("123 Main Street, Bangalore");

        log.info("✔ Creating Ledger...");
        MvcResult result = mockMvc.perform(post("/ledger")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode jsonResponse = objectMapper.readTree(result.getResponse().getContentAsString());
        ledgerId = jsonResponse.path("data").path("ledgerId").asLong();

        assertNotNull(ledgerId, "Ledger ID should not be null after creation.");
    }

    @Test
    @Order(2)
    @DisplayName("2. Create a Bill for the Ledger")
    void createBill() throws Exception {
        BillsRequest billsRequest = new BillsRequest();
        billsRequest.setTranDate(LocalDate.of(2025, 4, 17));
        billsRequest.setDueDate(LocalDate.of(2025, 4, 30));
        billsRequest.setDescription(BILL_DESCRIPTION);
        billsRequest.setReferenceBillNo("BILL-2025-0417");
        billsRequest.setTotalAmount(15000.75);
        billsRequest.setStatus(AmountStatus.UNPAID);

        log.info("✔ Creating Bill...");
        MvcResult billResult = mockMvc.perform(post("/bills")
                        .param("ledgerId", String.valueOf(ledgerId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(billsRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode billJson = objectMapper.readTree(billResult.getResponse().getContentAsString());
        billId = billJson.path("data").path("billId").asLong();

        assertNotNull(billId, "Bill ID should not be null after creation.");
    }

    @Test
    @Order(3)
    @DisplayName("3. Add First Bill Line")
    void addFirstBillLine() throws Exception {
        billLineId1 = createBillLine(BILL_DESCRIPTION, 5, 1000.15);
        assertNotNull(billLineId1, "First Bill Line ID should not be null.");
    }

    @Test
    @Order(4)
    @DisplayName("4. Add Second Bill Line")
    void addSecondBillLine() throws Exception {
        billLineId2 = createBillLine(BILL_DESCRIPTION, 5, 2000);
        assertNotNull(billLineId2, "Second Bill Line ID should not be null.");
    }

    @Test
    @Order(5)
    @DisplayName("5. Update Bill (Status and Amount)")
    void updateBill() throws Exception {
        BillsRequest updateRequest = new BillsRequest();
        updateRequest.setTranDate(LocalDate.of(2025, 4, 17));
        updateRequest.setDueDate(LocalDate.of(2025, 4, 30));
        updateRequest.setDescription(BILL_DESCRIPTION);
        updateRequest.setReferenceBillNo("BILL-2025-0417");
        updateRequest.setTotalAmount(16000);
        updateRequest.setStatus(AmountStatus.PAID);

        log.info("✔ Updating Bill...");
        mockMvc.perform(put("/bills/" + billId)
                        .param("ledgerId", String.valueOf(ledgerId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/bills/" + billId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.status").value(AmountStatus.PAID.name()))
                .andExpect(jsonPath("$.data.totalAmount").value(16000));
    }

    @Test
    @Order(6)
    @DisplayName("6. Update First Bill Line")
    void updateFirstBillLine() throws Exception {
        BillLineRequest updateRequest = new BillLineRequest();
        updateRequest.setDescription(BILL_DESCRIPTION);
        updateRequest.setQuantity(6);
        updateRequest.setUnitPrice(1000);

        log.info("✔ Updating First Bill Line...");
        mockMvc.perform(put("/bill-lines/" + billLineId1 + "/update")
                        .param("billId", String.valueOf(billId))
                        .param("itemId", ITEM_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/bill-lines/" + billLineId1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.quantity").value(6))
                .andExpect(jsonPath("$.data.unitPrice").value(1000));
    }

    @Test
    @Order(7)
    @DisplayName("7. Delete Bill and Verify Deletion")
    void deleteBill() throws Exception {
        log.info("✔ Deleting Bill...");
        mockMvc.perform(delete("/bills/" + billId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/bills/" + billId))
                .andExpect(status().isNotFound());
    }

    /**
     * Helper method to create a bill line
     */
    private Long createBillLine(String description, int quantity, double unitPrice) throws Exception {
        BillLineRequest request = new BillLineRequest();
        request.setDescription(description);
        request.setQuantity(quantity);
        request.setUnitPrice(unitPrice);

        MvcResult result = mockMvc.perform(post("/bill-lines")
                        .param("billId", String.valueOf(billId))
                        .param("itemId", ITEM_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode response = objectMapper.readTree(result.getResponse().getContentAsString());
        return response.path("data").path("billLineId").asLong();
    }
}
