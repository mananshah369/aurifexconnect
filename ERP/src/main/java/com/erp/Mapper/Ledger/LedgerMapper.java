package com.erp.Mapper.Ledger;

import com.erp.Dto.Request.LedgerRequest;
import com.erp.Dto.Response.LedgerResponse;
import com.erp.Model.Ledger;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface LedgerMapper {

    Ledger maptoLedger(LedgerRequest ledgerRequest);

    void mapToLedgerEntity(LedgerRequest ledgerRequest, @MappingTarget Ledger ledger);

    LedgerResponse mapToLedgerResponse(Ledger ledger);

    List<LedgerResponse> mapToLedgerResponse(List<Ledger> ledger);

}
