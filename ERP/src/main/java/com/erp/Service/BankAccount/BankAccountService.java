package com.erp.Service.BankAccount;

import com.erp.Dto.Request.BankAccountRequest;
import com.erp.Dto.Response.BankAccountResponse;

import java.util.List;

public interface BankAccountService {

    BankAccountResponse createBankAccount(BankAccountRequest bankAccountRequest,long ledgerId);

    List<BankAccountResponse> getAllBankAccounts();

    BankAccountResponse updateBankAccount(BankAccountRequest bankAccountRequest, long id);

    BankAccountResponse findByBankAccountId(long bankAccountId);

    BankAccountResponse deleteByBankAccountId(long bankAccountId);
}
