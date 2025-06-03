package com.erp.Service.BranchService;

import com.erp.Dto.Request.BranchRequest;
import com.erp.Dto.Request.CommonParam;
import com.erp.Dto.Response.BranchResponse;

import java.util.List;

public interface BranchService {

    BranchResponse createBranch(BranchRequest branchRequest);

    BranchResponse updateBranch(BranchRequest branchRequest);

    List<BranchResponse> getAllBranches();

    BranchResponse deleteBranchById(CommonParam param);

    List<BranchResponse> getByIdOrBranchName(CommonParam param);

    List<BranchResponse> getBranchesByItemName(CommonParam param);

}
