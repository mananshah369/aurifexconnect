package com.erp.Service.Master;

import com.erp.Dto.Request.MasterRequest;
import com.erp.Dto.Response.MasterResponse;
import com.erp.Enum.AccountStatus;
import com.erp.Enum.ReferenceType;
import com.erp.Enum.TransactionStatus;
import com.erp.Enum.VoucherType;
import com.erp.Exception.BankAccount.BankAccountNotFoundException;
import com.erp.Exception.BankAccount.InactiveBankAccountException;
import com.erp.Exception.Ledger.LedgerNotFoundException;
import com.erp.Exception.Master.MasterNotFoundException;
import com.erp.Exception.Voucher.VoucherNotFound;
import com.erp.Mapper.Master.MasterMapper;
import com.erp.Model.BankAccount;
import com.erp.Model.Ledger;
import com.erp.Model.Master;
import com.erp.Model.Voucher;
import com.erp.Repository.BankAccount.BankAccountRepository;
import com.erp.Repository.Ledger.LedgerRepository;
import com.erp.Repository.Master.MasterRepository;
import com.erp.Repository.Voucher.VoucherRepository;
import com.erp.Service.Voucher.VoucherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class MasterServiceImpl implements MasterService{

    private final MasterRepository masterRepository;
    private final VoucherService voucherService;
    private final MasterMapper masterMapper;
    private final LedgerRepository ledgerRepository;
    private final BankAccountRepository bankAccountRepository;
    private final VoucherRepository voucherRepository;

    @Override
    public MasterResponse createMaster(MasterRequest masterRequest, long ledgerId) {

        Ledger ledger = ledgerRepository.findById(ledgerId)
                .orElseThrow(()-> new LedgerNotFoundException("Ledger not found by this id : "+ledgerId+" , Invalid LedgerId"));

        if (masterRequest.getVoucherType() == null) {
            throw new VoucherNotFound("Voucher type must not be null or empty , Voucher Not Found ");
        }

        Voucher voucher = voucherService.generateFormattedVoucherId(masterRequest.getVoucherType());

        Master master = masterMapper.mapToMasterRequest(masterRequest);
        master.setLedger(ledger);
        master.setVoucher(voucher);
        String formattedVoucherId = voucherService.getFormattedVoucherId(voucher);

        master.setVoucherIndex(formattedVoucherId); // Store the formatted voucher ID in Master

        switch (masterRequest.getVoucherType()) {
            case SALES      -> handleInvoice(master);
            case PURCHASE   -> handleBill(master);
            case RECEIPTS   -> handleReceipt(master, masterRequest);
            case PAYMENTS   -> handlePayment(master, masterRequest);
            default         -> throw new VoucherNotFound("Voucher Not Found By This Voucher type : "+masterRequest.getVoucherType()+" ,Invalid Voucher Type");
        }

        masterRepository.save(master);
        return masterMapper.mapToMasterResponse(master);
    }

    @Override
    public MasterResponse findById(long masterId) {
        Master master = masterRepository.findById(masterId)
                .orElseThrow(()-> new MasterNotFoundException("Master Not found By Id , Invalid master Id"));

        return masterMapper.mapToMasterResponse(master);
    }

//    @Override
//    public MasterResponse updateMaster(Long masterId, MasterRequest masterRequest) {
//        Master existingMaster = masterRepository.findById(masterId)
//                .orElseThrow(() -> new InvoiceNotFoundException("Master not found with id: " + masterId));
//
//        Ledger ledger = ledgerRepository.findById(existingMaster.getLedger().getLedgerId())
//                .orElseThrow(() -> new LedgerNotFoundException("Ledger not found"));
//
//        Voucher voucher = voucherRepository.findByVoucherType(masterRequest.getVoucherType())
//                .orElseThrow(() -> new VoucherNotFound("Voucher Not Found By Voucher Type"));
//
//        masterMapper.mapToMasterEntity(masterRequest,existingMaster);
//
//        existingMaster.setVoucher(voucher);
//        existingMaster.setLedger(ledger);
//        existingMaster.setVoucherType(masterRequest.getVoucherType());
//        existingMaster.setFinancialYear(masterRequest.getFinancialYear());
//        existingMaster.setVoucherIndex(voucher.getVoucherIndex());
//
//        switch (masterRequest.getVoucherType()) {
//            case SALES      -> handleInvoice(existingMaster);
//            case PURCHASE   -> handleBill(existingMaster);
//            case RECEIPTS   -> handleReceipt(existingMaster, masterRequest);
//            case PAYMENTS   -> handlePayment(existingMaster, masterRequest);
//            default         -> throw new VoucherNotFound("Invalid voucher type: " + masterRequest.getVoucherType());
//        }
//
//        masterRepository.save(existingMaster);
//        return masterMapper.mapToMasterResponse(existingMaster);
//    }


    private void handleInvoice(Master master){
        master.setTransactionStatus(TransactionStatus.UNPAID);
    }

    private void handleBill(Master master) {
        master.setTransactionStatus(TransactionStatus.UNPAID);
    }

    private void handleReceipt(Master master, MasterRequest masterRequest) {
        BankAccount bankAccount = bankAccountRepository.findById(masterRequest.getBankAccountId())
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account not found , Invalid bank Account Id"));

        if (bankAccount.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new InactiveBankAccountException("Bank account is INACTIVE. Please contact admin.");
        }

        Master invoice = masterRepository.findById(masterRequest.getInvoiceId())
                .orElseThrow(() -> new MasterNotFoundException("Invoice not found with id: " + masterRequest.getInvoiceId()));

        if (invoice.getVoucherType() != VoucherType.SALES) {
            throw new VoucherNotFound("Provided related ID is not of type SALES");
        }

        List<Master> receipts = masterRepository.findByVoucherTypeAndMasterId(invoice.getVoucherType(),invoice.getMasterId());

        double totalPaidSoFar = receipts.stream().mapToDouble(Master::getAmount).sum();
        double newPayment = master.getAmount();
        double invoiceTotal = invoice.getAmount();
        double newTotalPaid = totalPaidSoFar + newPayment;

        if (newTotalPaid > invoiceTotal) {
            master.setReferenceType(ReferenceType.ADVANCEREF);
        }

        bankAccount.setCurrentBalance(bankAccount.getCurrentBalance() + newPayment);
        master.setBankAccount(bankAccount);

        // Update invoice status
        if (Double.compare(newTotalPaid, invoiceTotal) == 0) {
            invoice.setTransactionStatus(TransactionStatus.PAID);
        } else {
            invoice.setTransactionStatus(TransactionStatus.PARTIALLY_PAID);
        }

        master.setReferenceType(ReferenceType.NEWREF);
    }


    private void handlePayment(Master master, MasterRequest masterRequest) {
        BankAccount bankAccount = bankAccountRepository.findById(masterRequest.getBankAccountId())
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account not found , Invalid bank Account Id"));

        if (bankAccount.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new InactiveBankAccountException("Bank account is INACTIVE. Please contact admin.");
        }

        Master bill = masterRepository.findById(masterRequest.getBillId())  // assuming you renamed invoiceId to billId in DTO
                .orElseThrow(() -> new MasterNotFoundException("Bill not found with id: " + masterRequest.getBillId()));

        if (bill.getVoucherType() != VoucherType.PURCHASE) {
            throw new VoucherNotFound("Provided related ID is not of type PURCHASE");
        }

        List<Master> previousPayments = masterRepository.findByVoucherTypeAndMasterId(bill.getVoucherType(), bill.getMasterId());

        double totalPaidSoFar = previousPayments.stream().mapToDouble(Master::getAmount).sum();
        double newPayment = master.getAmount();
        double billTotal = bill.getAmount();
        double newTotalPaid = totalPaidSoFar + newPayment;

        if (newTotalPaid > billTotal) {
            master.setReferenceType(ReferenceType.ADVANCEREF);
        } else {
            master.setReferenceType(ReferenceType.AGAINREF);
        }

        // Deduct payment from bank account
        bankAccount.setCurrentBalance(bankAccount.getCurrentBalance() - newPayment);
        master.setBankAccount(bankAccount);

        // Update bill status
        if (Double.compare(newTotalPaid, billTotal) == 0) {
            bill.setTransactionStatus(TransactionStatus.PAID);
        } else {
            bill.setTransactionStatus(TransactionStatus.PARTIALLY_PAID);
        }
    }

//    @Override
//    public MasterResponse deleteMaster(Long masterId) {
//        Master master = masterRepository.findById(masterId)
//                .orElseThrow(() -> new MasterNotFoundException("Master not found with id: " + masterId));
//
//        VoucherType voucherType = master.getVoucherType();
//
//        switch (voucherType) {
//            case RECEIPTS -> {
//                BankAccount bankAccount = master.getBankAccount();
//                if (bankAccount != null) {
//                    bankAccount.setCurrentBalance(bankAccount.getCurrentBalance() - master.getAmount());
//                    bankAccountRepository.save(bankAccount);
//                }
//
//                if (master.getReferenceType() == ReferenceType.NEWREF || master.getReferenceType() == ReferenceType.ADVANCEREF) {
//                    Master invoice = masterRepository.findById(master.getMasterId())
//                            .orElseThrow(() -> new InvoiceNotFoundException("Linked invoice not found"));
//
//                    List<Master> relatedReceipts = masterRepository.findByVoucherTypeAndMasterId(VoucherType.SALES, invoice.getMasterId());
//                    double totalPaid = relatedReceipts.stream()
//                            .filter(p -> !Objects.equals(p.getMasterId(), masterId))
//                            .mapToDouble(Master::getAmount)
//                            .sum();
//
//                    if (Double.compare(totalPaid, 0.0) == 0) {
//                        invoice.setTransactionStatus(TransactionStatus.UNPAID);
//                    } else if (Double.compare(totalPaid, invoice.getAmount()) == 0) {
//                        invoice.setTransactionStatus(TransactionStatus.PAID);
//                    } else {
//                        invoice.setTransactionStatus(TransactionStatus.PARTIALLY_PAID);
//                    }
//                    masterRepository.save(invoice);
//                }
//            }
//
//            case PAYMENTS -> {
//                BankAccount bankAccount = master.getBankAccount();
//                if (bankAccount != null) {
//                    bankAccount.setCurrentBalance(bankAccount.getCurrentBalance() + master.getAmount());
//                    bankAccountRepository.save(bankAccount);
//                }
//
//                if (master.getReferenceType() == ReferenceType.AGAINREF || master.getReferenceType() == ReferenceType.ADVANCEREF) {
//                    Master bill = masterRepository.findById(master.getMasterId())
//                            .orElseThrow(() -> new InvoiceNotFoundException("Linked bill not found"));
//
//                    List<Master> relatedPayments = masterRepository.findByVoucherTypeAndMasterId(VoucherType.PURCHASE, bill.getMasterId());
//                    double totalPaid = relatedPayments.stream()
//                            .filter(p -> !Objects.equals(p.getMasterId(), masterId))
//                            .mapToDouble(Master::getAmount)
//                            .sum();
//
//                    if (Double.compare(totalPaid, 0.0) == 0) {
//                        bill.setTransactionStatus(TransactionStatus.UNPAID);
//                    } else if (Double.compare(totalPaid, bill.getAmount()) == 0) {
//                        bill.setTransactionStatus(TransactionStatus.PAID);
//                    } else {
//                        bill.setTransactionStatus(TransactionStatus.PARTIALLY_PAID);
//                    }
//                    masterRepository.save(bill);
//                }
//            }
//
//            case SALES, PURCHASE -> {
//                // Nothing extra needed unless you handle cascading deletes or specific logic.
//            }
//
//            default -> throw new VoucherNotFound("Invalid voucher type: " + voucherType);
//        }
//
//        masterRepository.deleteById(masterId);
//        return masterMapper.mapToMasterResponse(master);
//    }


}
