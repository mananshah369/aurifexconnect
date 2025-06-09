package com.erp.Service.BranchService;

import com.erp.Dto.Request.BranchRequest;
import com.erp.Dto.Request.CommanParam;
import com.erp.Dto.Response.BranchResponse;

import java.util.List;

public interface BranchService {

    BranchResponse createBranch(BranchRequest branchRequest);

    BranchResponse updateBranch(BranchRequest branchRequest);

    List<BranchResponse> getAllBranches();

    BranchResponse deleteBranchById(CommanParam param);

    List<BranchResponse> getByIdOrBranchName(CommanParam param);

    List<BranchResponse> getBranchesByItemName(CommanParam param);

}
