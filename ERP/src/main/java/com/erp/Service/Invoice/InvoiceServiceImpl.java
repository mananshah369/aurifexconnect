package com.erp.Service.Invoice;

import com.erp.Exception.Master.MasterNotFoundException;
import com.erp.Model.InvoiceGenerator;
import com.erp.Model.Master;
import com.erp.Repository.Invoice.InvoiceRepository;
import com.erp.Repository.Master.MasterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService{

    private final MasterRepository masterRepository;
    private final InvoiceRepository invoiceRepository;

    @Override
    public InvoiceGenerator createInvoice(long masterId) {

        Master master = masterRepository.findById(masterId)
                .orElseThrow(()-> new MasterNotFoundException("Master with" + masterId + " that id Not Found"));

        InvoiceGenerator invoice = new InvoiceGenerator();
        invoice.setItems(master.getLineItems());
        invoice.setLedger(master.getLedger());
        invoice.setMaster(master);
        invoice.setTotalAmount(master.getAmount());

        return invoiceRepository.save(invoice);
    }


    @Override
    public InvoiceGenerator fetchInvoice(long masterId){

        InvoiceGenerator invoice = invoiceRepository.findById(masterId)
                .orElseThrow(()-> new MasterNotFoundException("Master Not Found"));

        invoice.setItems(invoice.getMaster().getLineItems());

        return invoice;
    }


}

