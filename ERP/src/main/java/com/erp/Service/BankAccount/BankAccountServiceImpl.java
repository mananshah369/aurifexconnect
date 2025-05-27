package com.erp.Service.BankAccount;

import com.erp.Dto.Request.BankAccountRequest;
import com.erp.Dto.Response.BankAccountResponse;
import com.erp.Exception.BankAccount.BankAccountNotFoundException;
import com.erp.Exception.Ledger.LedgerNotFoundException;
import com.erp.Mapper.BankAccount.BankAccountMapper;
import com.erp.Model.BankAccount;
import com.erp.Model.Ledger;
import com.erp.Repository.BankAccount.BankAccountRepository;
import com.erp.Repository.Ledger.LedgerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BankAccountServiceImpl implements BankAccountService{

    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;
    private final LedgerRepository ledgerRepository;

    @Override
    public BankAccountResponse createBankAccount(BankAccountRequest bankAccountRequest, long ledgerId){
        Ledger ledger = ledgerRepository.findById(ledgerId)
                .orElseThrow(() -> new LedgerNotFoundException("Ledger not found , invalid ledger Id"));


        BankAccount bankAccount = bankAccountMapper.mapToBankAccount(bankAccountRequest);
        bankAccount.setCurrentBalance(bankAccount.getOpeningBalance());
        bankAccount.setLedger(ledger);
        bankAccountRepository.save(bankAccount);
        return bankAccountMapper.mapToBankAccountResponse(bankAccount);
    }

    @Override
    public BankAccountResponse updateBankAccount(BankAccountRequest bankAccountRequest, long id){
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(()-> new BankAccountNotFoundException("Bank Account Not Found! Invalid Id"));

        bankAccountMapper.mapToBankAccountEntity(bankAccountRequest, bankAccount);
        bankAccountRepository.save(bankAccount);
        return bankAccountMapper.mapToBankAccountResponse(bankAccount);
    }

    @Override
    public BankAccountResponse findByBankAccountId(long bankAccountId){
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(()->new BankAccountNotFoundException("Bank Account Not Found! Invalid Id"));
        return bankAccountMapper.mapToBankAccountResponse(bankAccount);
    }

    @Override
    public BankAccountResponse deleteByBankAccountId(long bankAccountId){
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(()-> new BankAccountNotFoundException("Bank Account Not Found! Invalid Id"));
        bankAccountRepository.deleteById(bankAccountId);
        return bankAccountMapper.mapToBankAccountResponse(bankAccount);
    }

    @Override
    public List<BankAccountResponse> getAllBankAccounts(){
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        return bankAccounts.stream()
                .map(bankAccountMapper::mapToBankAccountResponse)
                .collect(Collectors.toList());
    }

}