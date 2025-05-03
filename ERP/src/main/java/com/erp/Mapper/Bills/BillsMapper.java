package com.erp.Mapper.Bills;

import com.erp.Dto.Request.BillsRequest;
import com.erp.Dto.Response.BillsResponse;
import com.erp.Model.Bills;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface BillsMapper {

    Bills mapToBills (BillsRequest billsRequest);

    BillsResponse mapToBillListOfResponse(Bills bills);

    void mapToEntity (BillsRequest billsRequest,@MappingTarget Bills bills);

    List<BillsResponse> mapToBillListOfResponse(List<Bills> bills);


}
