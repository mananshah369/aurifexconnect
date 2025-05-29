package com.erp.Service.BranchService;

import com.erp.Dto.Request.BranchRequest;
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
    public BranchResponse updateBranch(Long branchId, BranchRequest branchRequest){
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(()-> new BranchNotFoundException("Branch not found with Id: " + branchId));

        branchMapper.mapToBranchEntity(branchRequest,branch);
        branchRepository.save(branch);
        return branchMapper.mapToBranchResponse(branch);
    }

    @Override
    public BranchResponse findBranchById(Long branchId){
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(()-> new BranchNotFoundException("Branch not Found, Invalid Id"));
        return branchMapper.mapToBranchResponse(branch);
    }

    @Override
    public BranchResponse deleteBranchById(Long branchId){
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(()-> new BranchNotFoundException("Branch Not Found, Invalid Id "+branchId));
        branchRepository.deleteById(branchId);
        return branchMapper.mapToBranchResponse(branch);
    }

    @Override
    public List<BranchResponse> getAllBranches(){
        List<Branch> branches = branchRepository.findAll();
        return branchMapper.mapToBranchResponse(branches);
    }

    @Override
    public List<BranchResponse> getBranchByName(String branchName) {
        List<Branch> branches = branchRepository.findByBranchName(branchName);

        if (branches.isEmpty()) {
            throw new BranchNotFoundException("No branches found with name: " + branchName);
        }

        return branchMapper.mapToBranchResponse(branches);
    }

    @Override
    public List<BranchResponse> getBranchesByItemName(String itemName){
        List<Branch> branches = branchRepository.findBranchByInventories_ItemName(itemName);

        if(branches.isEmpty()){
            throw new InventoryNotFoundException("No Branches found Stocking Item: "+itemName);
        }
        return branchMapper.mapToBranchResponse(branches);
    }
}
