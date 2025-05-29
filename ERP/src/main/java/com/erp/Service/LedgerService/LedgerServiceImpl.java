package com.erp.Service.LedgerService;

import com.erp.Dto.Request.LedgerRequest;
import com.erp.Dto.Response.LedgerResponse;
import com.erp.Exception.Ledger.LedgerNotFoundException;
import com.erp.Mapper.Ledger.LedgerMapper;
import com.erp.Model.Ledger;
import com.erp.Repository.Ledger.LedgerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LedgerServiceImpl implements LedgerService {

    private final LedgerRepository ledgerRepository;
    private final LedgerMapper ledgerMapper;

    @Override
    public LedgerResponse createLedger(LedgerRequest ledgerRequest){
        Ledger ledger = ledgerMapper.maptoLedger(ledgerRequest);
        ledgerRepository.save(ledger);
        return ledgerMapper.mapToLedgerResponse(ledger);
    }

    @Override
    public LedgerResponse updateLedgerInfo(LedgerRequest ledgerRequest, long id){
        Ledger ledger = ledgerRepository.findById(id)
                .orElseThrow(()-> new LedgerNotFoundException("Ledger Not Found! Invalid Id"));

        ledgerMapper.mapToLedgerEntity(ledgerRequest, ledger);
        ledgerRepository.save(ledger);
        return ledgerMapper.mapToLedgerResponse(ledger);
    }

    @Override
    public LedgerResponse findByLedgerId(long ledgerId) {
        Ledger ledger = ledgerRepository.findById(ledgerId)
                .orElseThrow(()-> new LedgerNotFoundException("Ledger Not Found! Invalid Id"));
        return ledgerMapper.mapToLedgerResponse(ledger);
    }

    @Override
    public LedgerResponse deleteByLedgerId(long ledgerId){
        Ledger ledger = ledgerRepository.findById(ledgerId)
                .orElseThrow(()-> new LedgerNotFoundException("Ledger Not Found! Invalid Id"));
        ledgerRepository.deleteById(ledgerId);
        return ledgerMapper.mapToLedgerResponse(ledger);
    }

    @Override
    public List<LedgerResponse> getAllLedger(){
        List<Ledger> ledgers = ledgerRepository.findAll();

        if (ledgers.isEmpty()) {
            throw new LedgerNotFoundException("No ledgers found ");
        }else {
            return ledgers.stream()
                    .map(ledgerMapper::mapToLedgerResponse)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<LedgerResponse> getLedgerByName(String ledgerName) {
        List<Ledger> ledgers = ledgerRepository.findByName(ledgerName);

        if (ledgers.isEmpty()) {
            throw new LedgerNotFoundException("No ledger found with name: " + ledgerName);
        }else {
            return ledgerMapper.mapToLedgerResponse(ledgers);
        }
    }
}
