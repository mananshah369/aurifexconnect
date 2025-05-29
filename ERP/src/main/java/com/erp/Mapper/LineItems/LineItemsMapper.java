package com.erp.Mapper.LineItems;

import com.erp.Dto.Response.LineItemsResponse;
import com.erp.Model.LineItems;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LineItemsMapper {
    
    LineItemsResponse mapToLineItem(LineItems lineItems);

    List<LineItems> mapToLineItem(List<LineItems> lineItems);
}
