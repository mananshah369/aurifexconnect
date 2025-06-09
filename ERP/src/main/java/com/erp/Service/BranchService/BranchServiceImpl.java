package com.erp.Service.BranchService;

import com.erp.Dto.Request.BranchRequest;
import com.erp.Dto.Request.CommonParam;
import com.erp.Dto.Response.BranchResponse;
import com.erp.Exception.Branch_Exception.BranchNotFoundException;
import com.erp.Exception.Inventory_Exception.InventoryNotFoundException;
import com.erp.Mapper.Branch.BranchMapper;
import com.erp.Model.Branch;
import com.erp.Repository.Branch.BranchRepository;
import com.erp.Repository.Inventory.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BranchServiceImpl implements BranchService{

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;
    private final InventoryRepository inventoryRepository;

    @Override
    public BranchResponse createBranch(BranchRequest branchRequest){
        Branch branch = branchMapper.mapToBranch(branchRequest);
        branchRepository.save(branch);
        return branchMapper.mapToBranchResponse(branch);
    }

    @Override
    public BranchResponse updateBranch(BranchRequest branchRequest){
        Branch branch = branchRepository.findById(branchRequest.getId())
                .orElseThrow(()-> new BranchNotFoundException("Branch not found with Id: " + branchRequest.getId()));

        branchMapper.mapToBranchEntity(branchRequest,branch);
        branchRepository.save(branch);
        return branchMapper.mapToBranchResponse(branch);
    }

    @Override
    public BranchResponse deleteBranchById(CommonParam param){
        Branch branch = branchRepository.findById(param.getId())
                .orElseThrow(()-> new BranchNotFoundException("Branch Not Found, Invalid Id "+param.getId()));
        branchRepository.deleteById(param.getId());
        return branchMapper.mapToBranchResponse(branch);
    }

    @Override
    public List<BranchResponse> getAllBranches(){
        List<Branch> branches = branchRepository.findAll();
        return branchMapper.mapToBranchResponse(branches);
    }

    @Override
    public List<BranchResponse> getByIdOrBranchName(CommonParam param) {
        List<Branch> branches = branchRepository.findByBranchIdOrBranchName(param.getId(),param.getName());
        if (branches.isEmpty()) {
            throw new BranchNotFoundException("No branches Found, Invalid Id ");
        }else {
            return branchMapper.mapToBranchResponse(branches);
        }
    }

    @Override
    public List<BranchResponse> getBranchesByItemName(CommonParam param){
        List<Branch> branches = branchRepository.findBranchByInventories_ItemName(param.getName());

        if(branches.isEmpty()){
            throw new InventoryNotFoundException("No Branches found Stocking Item: "+param.getName());
        }
        return branchMapper.mapToBranchResponse(branches);
    }
}