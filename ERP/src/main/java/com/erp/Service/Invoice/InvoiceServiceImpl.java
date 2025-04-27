package com.erp.Service.Invoice;

import com.erp.Dto.Request.InvoiceRequest;
import com.erp.Dto.Response.InvoiceResponse;
import com.erp.Enum.InvoiceStatus;
import com.erp.Exception.Customer.CustomerNotFoundException;
import com.erp.Exception.Invoice_Exception.InvoiceNotFoundException;
import com.erp.Mapper.Invoice.InvoiceMapper;
import com.erp.Model.Customer;
import com.erp.Model.Invoice;
import com.erp.Repository.Customer.CustomerRepository;
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
    private final CustomerRepository customerRepository;

    @Override
    public InvoiceResponse create(InvoiceRequest request,long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer Not Found , Invalid Id"));

        Invoice invoice = invoiceMapper.mapToInvoice(request);
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setInvoiceStatus(InvoiceStatus.DRAFT);
        invoice.setTotalAmount(0);
        invoice.setCustomer(customer);
        invoiceRepository.save(invoice);

        return invoiceMapper.mapToInvoiceResponse(invoice);
    }

    @Override
    public InvoiceResponse findById(long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice Not Found , Invalid Invoice Id"));

        return invoiceMapper.mapToInvoiceResponse(invoice);
    }

    @Override
    public List<InvoiceResponse> findByCustomer_Name(String customerName) {
        List<Invoice> invoice = invoiceRepository.findByCustomer_Name(customerName);

        if(invoice.isEmpty()){
            throw new CustomerNotFoundException("Invalid");
        }else {
            return invoiceMapper.mapToInvoicesResponse(invoice);
        }
    }

    @Override
    public List<InvoiceResponse> findByAllInvoice() {
        List<Invoice> invoice = invoiceRepository.findAll();
        return invoiceMapper.mapToInvoicesResponse(invoice);
    }

    @Override
    public InvoiceResponse delete(long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice Not Found , Invalid Invoice Id"));

        invoiceRepository.deleteById(invoiceId);
        return invoiceMapper.mapToInvoiceResponse(invoice);
    }

}
