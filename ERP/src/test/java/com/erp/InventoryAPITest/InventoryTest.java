package com.erp.InventoryAPITest;

import com.erp.Dto.Request.InventoryRequest;
import com.erp.Mapper.Inventory.InventoryMapper;
import com.erp.Model.Inventory;
import com.erp.Repository.Inventory.InventoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@SpringBootTest
public class InventoryTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static long inventoryId;

    @Test
    @Order(1)
    void testCreateItem() throws Exception {
        InventoryRequest inventoryRequest = new InventoryRequest();
        inventoryRequest.setItemName("Mouse Die Spry");
        inventoryRequest.setItemCost(1000);
        inventoryRequest.setItemQuantity(5);
        inventoryRequest.setItemDescription("Good For Health");

        Inventory inventory = inventoryMapper.mapToInventory(inventoryRequest);
        inventoryRepository.save(inventory);

        inventoryId = inventory.getItemId();

        mockMvc.perform(post("/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventoryRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    void testFindItemById() throws Exception {
        mockMvc.perform(get("/inventory/" + inventoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.").value("Mouse Die Spry"))
                .andExpect(jsonPath("$.data.itemCost").value(1000))
                .andExpect(jsonPath("$.data.itemQuantity").value("5"))
                .andExpect(jsonPath("$.data.itemDescription").value("Good For Health"));
    }

    @Test
    @Order(3)
    void testUpdateItemById() throws Exception {
        InventoryRequest updateRequest = new InventoryRequest();
        updateRequest.setItemName("Updated Mouse");
        updateRequest.setItemCost(1200);
        updateRequest.setItemQuantity(10);
        updateRequest.setItemDescription("Updated description");

        mockMvc.perform(put("/inventory/" + inventoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/inventory/" + inventoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.itemName").value("Updated Mouse"))
                .andExpect(jsonPath("$.data.itemCost").value(1200))
                .andExpect(jsonPath("$.data.itemQuantity").value("10"))
                .andExpect(jsonPath("$.data.itemDescription").value("Updated description"));
    }


    @Test
    @Order(4)
    void testFindItemByName() throws Exception {
        // Find the item by its name
        mockMvc.perform(get("/inventory?itemName=Updated Mouse"))
                .andExpect(status().isOk())  // Expect 200 OK status
                .andExpect(jsonPath("$.data[*].itemName").value("Updated Mouse"))  // Verify item name
                .andExpect(jsonPath("$.data[*].itemCost").value(1200.0))  // Verify item cost
                .andExpect(jsonPath("$.data[*].itemQuantity").value("10"))  // Verify item quantity
                .andExpect(jsonPath("$.data[*].itemDescription").value("Updated description"));  // Verify item description
    }

    @Test
    @Order(5)
    void testDeleteItemById() throws Exception {
        mockMvc.perform(delete("/inventory/" + inventoryId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/inventory/" + inventoryId))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(6)
    void cleanUp() {
        inventoryRepository.deleteAll();
    }
}
