package com.erp.Controller.Invoice;


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
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final PdfService pdfService;
    private final EmailService emailService;
    @PostMapping("invoice/{masterId}")
    public String invoicePreview(@PathVariable long masterId,
                                 Model model){
        InvoiceGenerator invoiceGenerator = invoiceService.createInvoice(masterId);

        model.addAttribute("invoice",invoiceGenerator);
        return "invoice-preview";
    }

    @PostMapping("/pdf/{masterId}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable long masterId){

        byte[] pdf = pdfService.generateInvoicePdf(masterId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice-" + masterId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }


    @PostMapping("invoice/{masterId}/sendMail")
    public ResponseEntity<String> sendInvoiceEmail(@PathVariable long masterId) throws MessagingException {

        InvoiceGenerator invoiceGenerator = invoiceService.fetchInvoice(masterId);

        String customerEmail = invoiceGenerator.getLedger().getEmail();

        byte[] pdf = pdfService.generateInvoicePdf(masterId);

        emailService.sendEmail(customerEmail,pdf);

        return  ResponseEntity.ok("Email Sent To : "+customerEmail);

    }

}
