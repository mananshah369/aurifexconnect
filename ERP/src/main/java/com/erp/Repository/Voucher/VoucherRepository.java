package com.erp.Repository.Voucher;

import com.erp.Enum.VoucherType;
import com.erp.Model.Voucher;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.time.LocalDate;
import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher,Long> {

    Optional<Voucher> findByVoucherType(VoucherType voucherType);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Voucher> findByVoucherTypeAndStartDate(VoucherType voucherType, LocalDate startDate);

}
