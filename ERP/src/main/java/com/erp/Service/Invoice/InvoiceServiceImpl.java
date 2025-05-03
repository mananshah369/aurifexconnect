package com.erp.Service.Invoice;

import com.erp.Dto.Request.InvoiceRequest;
import com.erp.Dto.Response.InvoiceResponse;
import com.erp.Enum.InvoiceStatus;
import com.erp.Exception.Ledger.LedgerNotFoundException;
import com.erp.Exception.Invoice_Exception.InvoiceNotFoundException;
import com.erp.Mapper.Invoice.InvoiceMapper;
import com.erp.Model.Ledger;
import com.erp.Model.Invoice;
import com.erp.Repository.Ledger.LedgerRepository;
import com.erp.Repository.Invoice.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService{

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;
    private final LedgerRepository ledgerRepository;

    @Override
    public InvoiceResponse create(InvoiceRequest request, long ledgerId) {

        Ledger ledger = ledgerRepository.findById(ledgerId)
                .orElseThrow(() -> new LedgerNotFoundException("Ledger Not Found , Invalid Id " + ledgerId));

        Invoice invoice = invoiceMapper.mapToInvoice(request);
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setInvoiceStatus(InvoiceStatus.DRAFT);
        invoice.setTotalAmount(0);
        invoice.setLedger(ledger);
        invoiceRepository.save(invoice);

        return invoiceMapper.mapToInvoiceResponse(invoice);
    }

    @Override
    public InvoiceResponse findById(long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice Not Found , Invalid Invoice Id " + invoiceId));

        return invoiceMapper.mapToInvoiceResponse(invoice);
    }

    @Override
    public List<InvoiceResponse> findByLedger_Name(String ledgerName) {
        List<Invoice> invoice = invoiceRepository.findByLedger_Name(ledgerName);

        if(invoice.isEmpty()){
            throw new LedgerNotFoundException("Invalid Ledger Name "+ledgerName);
        }else {
            return invoiceMapper.mapToInvoicesResponse(invoice);
        }
    }

    @Override
    public List<InvoiceResponse> findByAllInvoice() {
        List<Invoice> invoice = invoiceRepository.findAll();

        if (invoice.isEmpty()) {
            throw new InvoiceNotFoundException("No Invoices found");
        }else {
            return invoiceMapper.mapToInvoicesResponse(invoice);
        }
    }

    @Override
    public InvoiceResponse delete(long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice Not Found , Invalid Invoice Id "+invoiceId));

        invoiceRepository.deleteById(invoiceId);
        return invoiceMapper.mapToInvoiceResponse(invoice);
    }

}
