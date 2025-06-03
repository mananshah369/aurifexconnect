package com.erp.Controller.Invoice;


import com.erp.Dto.Request.InvoiceRequest;
import com.erp.Model.InvoiceGenerator;
import com.erp.Service.Invoice.EmailService;
import com.erp.Service.Invoice.InvoiceService;
import com.erp.Service.Invoice.PdfService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final PdfService pdfService;
    private final EmailService emailService;

    @PostMapping("invoice")
    public String invoicePreview(@RequestBody InvoiceRequest request,
                                 Model model){
        InvoiceGenerator invoiceGenerator = invoiceService.createInvoice(request.getMasterId());

        model.addAttribute("invoice",invoiceGenerator);
        return "invoice-preview";
    }

    @PostMapping("/pdf")
    public ResponseEntity<byte[]> downloadPdf(@RequestBody InvoiceRequest request){

        byte[] pdf = pdfService.generateInvoicePdf(request.getMasterId());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice-" + request.getMasterId() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }


    @PostMapping("invoice/sendMail")
    public ResponseEntity<String> sendInvoiceEmail(@RequestBody InvoiceRequest request) throws MessagingException {

        InvoiceGenerator invoiceGenerator = invoiceService.fetchInvoice(request.getMasterId());

        String customerEmail = invoiceGenerator.getLedger().getEmail();

        byte[] pdf = pdfService.generateInvoicePdf(request.getMasterId());

        emailService.sendEmail(customerEmail,pdf);

        return  ResponseEntity.ok("Email Sent To : "+customerEmail);

    }

}
