package com.erp.Mapper.InventoryMovement;

import com.erp.Dto.Request.InventoryMovementRequest;
import com.erp.Dto.Response.InventoryMovementResponse;
import com.erp.Model.InventoryMovement;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface InventoryMovementMapper {

    InventoryMovement mapToInventoryMovement(InventoryMovementRequest inventoryMovementRequest);
    InventoryMovementResponse mapToInventoryMovementResponse(InventoryMovement movement);
}