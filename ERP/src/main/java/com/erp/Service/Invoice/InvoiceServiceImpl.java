package com.erp.Service.Invoice;

import com.erp.Dto.Request.InvoiceRequest;
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
    public InvoiceGenerator createInvoice(InvoiceRequest request) {

        Master master = masterRepository.findById(request.getMasterId())
                .orElseThrow(()-> new MasterNotFoundException("Master with" + request.getMasterId() + " that id Not Found"));

        InvoiceGenerator invoice = new InvoiceGenerator();
        invoice.setItems(master.getLineItems());
        invoice.setLedger(master.getLedger());
        invoice.setMaster(master);
        invoice.setTotalAmount(master.getAmount());

        return invoiceRepository.save(invoice);
    }


    @Override
    public InvoiceGenerator fetchInvoice(InvoiceRequest request){

        InvoiceGenerator invoice = invoiceRepository.findById(request.getMasterId())
                .orElseThrow(()-> new MasterNotFoundException("Master Not Found"));

        invoice.setItems(invoice.getMaster().getLineItems());

        return invoice;
    }


}

