package com.erp.Service.BranchService;

import com.erp.Dto.Request.BranchRequest;
import com.erp.Dto.Response.BranchResponse;

import java.util.List;

public interface BranchService {

    BranchResponse createBranch(BranchRequest branchRequest);

    BranchResponse updateBranch(Long branchId, BranchRequest branchRequest);

    BranchResponse findBranchById(Long branchId);

    List<BranchResponse> getAllBranches();

    BranchResponse deleteBranchById(Long branchId);

    List<BranchResponse> getBranchByName(String branchName);

    List<BranchResponse> getBranchesByItemName(String itemName);

}
