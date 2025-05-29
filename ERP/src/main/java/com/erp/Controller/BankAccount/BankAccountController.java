package com.erp.Controller.BankAccount;

import com.erp.Dto.Request.BankAccountRequest;
import com.erp.Dto.Response.BankAccountResponse;
import com.erp.Service.BankAccount.BankAccountService;
import com.erp.Utility.ListResponseStructure;
import com.erp.Utility.ResponseBuilder;
import com.erp.Utility.ResponseStructure;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @PostMapping("bank-account/{ledgerId}")
    public ResponseEntity<ResponseStructure<BankAccountResponse>> createBankAccount(@Valid @RequestBody BankAccountRequest bankAccountRequest ,@PathVariable long ledgerId){
        BankAccountResponse bankAccountResponse = bankAccountService.createBankAccount(bankAccountRequest , ledgerId);
        return ResponseBuilder.success(HttpStatus.CREATED, "Bank Account Created", bankAccountResponse);
    }

    @PutMapping("bank-account/{id}")
    public ResponseEntity<ResponseStructure<BankAccountResponse>> updateBankAccount(@RequestBody BankAccountRequest bankAccountRequest, @PathVariable long id){
        BankAccountResponse bankAccountResponse = bankAccountService.updateBankAccount(bankAccountRequest, id);
        return ResponseBuilder.success(HttpStatus.OK,"Bank Account Updated Successfully", bankAccountResponse);
    }

    @GetMapping("bank-account/{bankAccountId}")
    public ResponseEntity<ResponseStructure<BankAccountResponse>> findByBankAccountId(@PathVariable long bankAccountId){
        BankAccountResponse bankAccountResponse = bankAccountService.findByBankAccountId(bankAccountId);
        return ResponseBuilder.success(HttpStatus.OK,"Bank Account Found Successfully!", bankAccountResponse);
    }

    @DeleteMapping("bank-account/{bankAccountId}")
    public ResponseEntity<ResponseStructure<BankAccountResponse>> deleteByBankAccountId(@PathVariable long bankAccountId){
        BankAccountResponse bankAccountResponse = bankAccountService.deleteByBankAccountId(bankAccountId);
        return ResponseBuilder.success(HttpStatus.OK,"Bank Account Deleted Successfully!", bankAccountResponse);
    }

    @GetMapping("bank-accounts")
    public ResponseEntity<ListResponseStructure<BankAccountResponse>> getAllBankAccounts(){
        List<BankAccountResponse> allBankAccounts = bankAccountService.getAllBankAccounts();
        return ResponseBuilder.success(HttpStatus.OK,"All Bank Accounts Fetched Successfully", allBankAccounts);
    }
}
