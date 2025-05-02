package com.erp.Repository.BillLine;

import com.erp.Model.BillLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillLineRepository extends JpaRepository<BillLine,Long> {

    List<BillLine> findByDescriptionContainingIgnoreCase(String description);
}
