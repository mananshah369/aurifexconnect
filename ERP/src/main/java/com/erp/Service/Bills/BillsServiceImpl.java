package com.erp.Service.Bills;

import com.erp.Dto.Request.BillsRequest;
import com.erp.Dto.Response.BillsResponse;
import com.erp.Exception.Bills.BillNotFoundException;
import com.erp.Exception.Ledger.LedgerNotFoundException;
import com.erp.Mapper.Bills.BillsMapper;
import com.erp.Model.Bills;
import com.erp.Model.Ledger;
import com.erp.Repository.Bills.BillsRepository;
import com.erp.Repository.Ledger.LedgerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BillsServiceImpl implements BillsService{

    private final BillsRepository billsRepository;
    private final BillsMapper billsMapper;
    private final LedgerRepository ledgerRepository;

    @Override
    public BillsResponse addBill(BillsRequest billsRequest, long ledgerId) {
        Ledger ledger = ledgerRepository.findById(ledgerId)
                .orElseThrow(()->new LedgerNotFoundException("Invalid Id !,Supplier not found !"));
        Bills bills = billsMapper.mapToBills(billsRequest);
        bills.setLedger(ledger);
        billsRepository.save(bills);
        return billsMapper.mapToBillListOfResponse(bills);
    }

    @Override
    public BillsResponse updateBill(BillsRequest billsRequest, long billId, long ledgerId) {
        Bills bills = billsRepository.findById(billId)
                .orElseThrow(()-> new BillNotFoundException("Invalid Id ! Bill Not Found !"));
        billsMapper.mapToEntity(billsRequest, bills);
        billsRepository.save(bills);
        return billsMapper.mapToBillListOfResponse(bills);

    }

    @Override
    public BillsResponse findByBillId(long billId) {
        Bills bills = billsRepository.findById(billId)
                .orElseThrow(()-> new BillNotFoundException("Invalid Id ! Bill Not Found !"));
        return billsMapper.mapToBillListOfResponse(bills);
    }

    @Override
    public BillsResponse deleteByBillId(long billId) {
        Bills bills = billsRepository.findById(billId)
                .orElseThrow(()-> new BillNotFoundException("Invalid Id ! Bill Not Found !"));
        billsRepository.deleteById(billId);
        return billsMapper.mapToBillListOfResponse(bills);
    }

    @Override
    public List<BillsResponse> findBillBySupplierName(String legerName) {
       List<Bills> bills = billsRepository.findByLedger_Name(legerName);

        if (bills.isEmpty()) {
            throw new LedgerNotFoundException("No Ledger Found By Name = "+legerName);
        }else {
            return billsMapper.mapToBillListOfResponse(bills);
        }
    }
}
