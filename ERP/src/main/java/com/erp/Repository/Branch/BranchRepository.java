package com.erp.Repository.Branch;

import com.erp.Model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    List<Branch> findByBranchIdOrBranchName(long branchId, String name);

    List<Branch> findBranchByInventories_ItemName(String itemName);
}
