package com.erp.InvoiceLineItemsAPITest;

import com.erp.Dto.Request.CustomerRequest;
import com.erp.Dto.Request.InventoryRequest;
import com.erp.Dto.Request.InvoiceRequest;
import com.erp.Exception.Inventory_Exception.InventoryNotFoundException;
import com.erp.Exception.Invoice_Exception.InvoiceNotFoundException;
import com.erp.Mapper.Customer.CustomerMapper;
import com.erp.Mapper.Inventory.InventoryMapper;
import com.erp.Mapper.Invoice.InvoiceMapper;
import com.erp.Model.*;
import com.erp.Repository.Customer.CustomerRepository;
import com.erp.Repository.Invoice.InvoiceRepository;
import com.erp.Repository.Inventory.InventoryRepository;
import com.erp.Repository.InvoiceLineItems.InvoiceLineItemsRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;


import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@SpringBootTest
public class InvoiceLineItemTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private InventoryRepository inventoryRepository;
    @Autowired private InvoiceRepository invoiceRepository;
    @Autowired private InvoiceLineItemsRepository invoiceLineItemsRepository;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private CustomerMapper customerMapper;
    @Autowired private InventoryMapper inventoryMapper;
    @Autowired private InvoiceMapper invoiceMapper;
    @Autowired private ObjectMapper objectMapper;

    private static long invoiceId;
    private static long inventoryId;
    private static long customerId;

    private static final LocalDate TEST_DATE = LocalDate.of(2025, 4, 23);

    @BeforeAll
    static void beforeAll() {
        System.out.println("Starting Invoice Line Item Test...");
    }

    @Test
    @Order(1)
    void testSetupCustomerInventoryAndInvoice() throws Exception {
        // Create and persist Customer via API
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setName("Kalu Kaliya");
        customerRequest.setEmail("kalu@gmail.com");
        customerRequest.setPhone("9876543210");
        customerRequest.setAddress("Bangalore, India");

        MvcResult customerResult = mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        String customerResponse = customerResult.getResponse().getContentAsString();
        JsonNode customerJson = objectMapper.readTree(customerResponse);
        customerId = customerJson.get("data").get("customerId").asLong();
        Assertions.assertNotNull(customerId);

        // Create and persist Inventory via API
        InventoryRequest inventoryRequest = new InventoryRequest();
        inventoryRequest.setItemName("Test Product");
        inventoryRequest.setItemCost(100.0);
        inventoryRequest.setItemQuantity(10);
        inventoryRequest.setItemDescription("Good Product");

        MvcResult inventoryResult = mockMvc.perform(post("/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventoryRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        String inventoryResponse = inventoryResult.getResponse().getContentAsString();
        JsonNode inventoryJson = objectMapper.readTree(inventoryResponse);
        inventoryId = inventoryJson.get("data").get("itemId").asLong();
        Assertions.assertNotNull(inventoryId);

        // Create and persist Invoice via API
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setDescription("Is Product Bill is Pending");

        MvcResult invoiceResult = mockMvc.perform(post("/invoice")
                        .param("customerId", String.valueOf(customerId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invoiceRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        String invoiceResponse = invoiceResult.getResponse().getContentAsString();
        JsonNode invoiceJson = objectMapper.readTree(invoiceResponse);
        invoiceId = invoiceJson.get("data").get("invoiceId").asLong();
        Assertions.assertNotNull(invoiceId);
    }


    @Test
    @Order(2)
    void testCreateInvoiceLineItem() throws Exception {
        // Fetch inventory and invoice from DB (they have been created in the previous test)
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not Found"));
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice Not Found"));
        double quantity = 2.0;

        // Create the invoice line item via the API
        mockMvc.perform(post("/create")
                        .param("inventoryId", String.valueOf(inventoryId))
                        .param("invoiceId", String.valueOf(invoiceId))
                        .param("customerId", String.valueOf(customerId))
                        .param("quantity", String.valueOf(quantity)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.date").value("2025-04-23"))
                .andExpect(jsonPath("$.data.itemName").value(inventory.getItemName()))
                .andExpect(jsonPath("$.data.unitPrice").value(inventory.getItemCost()))
                .andExpect(jsonPath("$.data.quantity").value(2.0))
                .andExpect(jsonPath("$.data.totalPrice").value(200.0));

        // Assert that the invoice total has been updated correctly
        Assertions.assertEquals(200.0, getInvoiceTotalAmount(invoiceId));

        // Assert that the inventory quantity has been reduced correctly
        Assertions.assertEquals(8, getInventoryAvailableQuantity(inventoryId));
    }


    @Test
    @Order(3)
    void testInvoiceLineItemFetchedByInvoiceId() throws Exception {
        mockMvc.perform(get("/invoiceLineItems/" + invoiceId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].date").value("2025-04-23"))
                .andExpect(jsonPath("$.data[0].itemName").value("Test Product"))
                .andExpect(jsonPath("$.data[0].unitPrice").value(100))
                .andExpect(jsonPath("$.data[0].quantity").value(2.0))
                .andExpect(jsonPath("$.data[0].totalPrice").value(200.0));
    }
    @Test
    @Order(4)
    void testUpdateInvoiceLineItem() throws Exception {
        double updatedQuantity = 3.0;

        // Call the update API (assuming endpoint exists)
        mockMvc.perform(put("/invoiceItems") // Use actual endpoint if different
                        .param("inventoryId", String.valueOf(inventoryId))
                        .param("invoiceId", String.valueOf(invoiceId))
                        .param("quantity", String.valueOf(updatedQuantity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.date").value("2025-04-23"))
                .andExpect(jsonPath("$.data.itemName").value("Test Product"))
                .andExpect(jsonPath("$.data.unitPrice").value(100))
                .andExpect(jsonPath("$.data.quantity").value(updatedQuantity))
                .andExpect(jsonPath("$.data.totalPrice").value(updatedQuantity * 100.0)); // Assuming itemCost = 100

        // Updated assertions
        Assertions.assertEquals(300.0, getInvoiceTotalAmount(invoiceId));  // 3 x 100
        Assertions.assertEquals(7, getInventoryAvailableQuantity(inventoryId)); // 10 - 3 used
    }

    @Test
    @Order(5)
    void testDeleteInvoiceLineItem() throws Exception {
        // Perform the delete operation via API
        mockMvc.perform(delete("/invoiceItems/delete")
                        .param("inventoryId", String.valueOf(inventoryId))
                        .param("invoiceId", String.valueOf(invoiceId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Invoice Item Deleted Successfully"));

        Assertions.assertEquals(0.0, getInvoiceTotalAmount(invoiceId));
        Assertions.assertEquals(10, getInventoryAvailableQuantity(inventoryId));
    }


    @Test
    @Order(6)
    void testCreateInvoiceLineItemWithInvalidInventoryId() throws Exception {
        mockMvc.perform(post("/create")
                        .param("inventoryId", "99999")
                        .param("invoiceId", String.valueOf(invoiceId))
                        .param("customerId", String.valueOf(customerId))
                        .param("quantity", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Inventory not found"));
    }

    @Test
    @Order(7)
    void testCreateInvoiceLineItemWithInvalidInvoiceId() throws Exception {
        mockMvc.perform(post("/create")
                        .param("inventoryId", String.valueOf(inventoryId))
                        .param("invoiceId", "99999")
                        .param("customerId", String.valueOf(customerId))
                        .param("quantity", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Invoice not found , Invalid Invoice Id"));
    }

    @Test
    @Order(8)
    void testCreateInvoiceLineItemWithInvalidCustomerId() throws Exception {
        mockMvc.perform(post("/create")
                        .param("inventoryId", String.valueOf(inventoryId))
                        .param("invoiceId", String.valueOf(invoiceId))
                        .param("customerId", "99999")
                        .param("quantity", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("This invoice does not belong to the customer."));
    }

    @Test
    @Order(9)
    void testCreateInvoiceLineItemWithExcessQuantity() throws Exception {
        mockMvc.perform(post("/create")
                        .param("inventoryId", String.valueOf(inventoryId))
                        .param("invoiceId", String.valueOf(invoiceId))
                        .param("customerId", String.valueOf(customerId))
                        .param("quantity", "100"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Insufficient Stock . Available: " + getInventoryAvailableQuantity(inventoryId)));
    }

    @Test
    @Order(10)
    void testCreateInvoiceLineItemWithNegativeQuantity() throws Exception {
        mockMvc.perform(post("/create")
                        .param("inventoryId", String.valueOf(inventoryId))
                        .param("invoiceId", String.valueOf(invoiceId))
                        .param("customerId", String.valueOf(customerId))
                        .param("quantity", "-1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Quantity must be a positive whole number (e.g., 1, 2, 5)"));
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Invoice Line Item Test Completed.");
    }

    // === Utility Methods ===

    private double getInvoiceTotalAmount(long invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found"))
                .getTotalAmount();
    }

    private double getInventoryAvailableQuantity(long inventoryId) {
        return inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found"))
                .getItemQuantity();
    }
}
