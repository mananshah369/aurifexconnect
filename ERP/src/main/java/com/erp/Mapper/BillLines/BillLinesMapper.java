package com.erp.Mapper.BillLines;


import com.erp.Dto.Request.BillLineRequest;
import com.erp.Dto.Response.BillLineResponse;
import com.erp.Model.BillLine;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface BillLinesMapper {

    BillLine mapTOBillLine(BillLineRequest billLineRequest);

    BillLineResponse mapToBillLineResponse(BillLine billLine);

    void mapToBillLineEntity(BillLineRequest billLineRequest,@MappingTarget BillLine billLine);

    List<BillLineResponse> mapToBillLineResponseList(List<BillLine> billLine);

}
