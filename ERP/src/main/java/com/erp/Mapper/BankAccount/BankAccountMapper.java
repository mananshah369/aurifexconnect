package com.erp.Mapper.BankAccount;

import com.erp.Dto.Request.BankAccountRequest;
import com.erp.Dto.Response.BankAccountResponse;
import com.erp.Model.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {

    BankAccount mapToBankAccount(BankAccountRequest bankAccountRequest);

    void mapToBankAccountEntity(BankAccountRequest bankAccountRequest, @MappingTarget BankAccount bankAccount);

    BankAccountResponse mapToBankAccountResponse(BankAccount bankAccount);

    List<BankAccountResponse> mapToBankAccountResponse(List<BankAccount> bankAccounts);
}
