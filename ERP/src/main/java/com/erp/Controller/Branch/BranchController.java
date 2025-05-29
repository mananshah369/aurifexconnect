package com.erp.Controller.Branch;

import com.erp.Dto.Request.BranchRequest;
import com.erp.Dto.Response.BranchResponse;
import com.erp.Service.BranchService.BranchService;
import com.erp.Utility.ListResponseStructure;
import com.erp.Utility.ResponseBuilder;
import com.erp.Utility.ResponseStructure;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class BranchController {

    private final BranchService branchService;

    @PostMapping("branch")
    public ResponseEntity<ResponseStructure<BranchResponse>> createBranch(@Valid @RequestBody BranchRequest branchRequest){
        BranchResponse branchResponse = branchService.createBranch(branchRequest);
        return ResponseBuilder.success(HttpStatus.CREATED,"Branch Created", branchResponse);
    }

    @PutMapping("/{branchId}")
    public ResponseEntity<ResponseStructure<BranchResponse>> updateBranch(@PathVariable Long branchId, @RequestBody BranchRequest branchRequest){
        BranchResponse branchResponse = branchService.updateBranch(branchId,branchRequest);
        return ResponseBuilder.success(HttpStatus.OK,"Branch Updated Successfully!", branchResponse);
    }

    @GetMapping("branch/{branchId}")
    public ResponseEntity<ResponseStructure<BranchResponse>> findBranchById(@PathVariable long branchId){
        BranchResponse branchResponse = branchService.findBranchById(branchId);
        return ResponseBuilder.success(HttpStatus.OK,"Branch Found Successfully!",branchResponse);
    }

    @DeleteMapping("branch/{branchId}")
    public ResponseEntity<ResponseStructure<BranchResponse>> deleteBranchById(@PathVariable long branchId){
        BranchResponse branchResponse = branchService.deleteBranchById(branchId);
        return ResponseBuilder.success(HttpStatus.OK,"Branch Deleted Successfully!",branchResponse);
    }

    @GetMapping
    public ResponseEntity<ListResponseStructure<BranchResponse>> getBranchByName(@RequestParam String branchName){
        List<BranchResponse> branchResponse = branchService.getBranchByName(branchName);
        return ResponseBuilder.success(HttpStatus.OK,"Branch Found Successfully",branchResponse);
    }

    @GetMapping("branch/all")
    public ResponseEntity<ListResponseStructure<BranchResponse>> getAllBranches(){
        List<BranchResponse> branchResponse = branchService.getAllBranches();
        return ResponseBuilder.success(HttpStatus.OK,"All Branches Found Successfully!",branchResponse);
    }

    @GetMapping("/branch/by-item/{itemName}")
    public ResponseEntity<ListResponseStructure<BranchResponse>> getBranchesByItemName(@PathVariable String itemName){
        List<BranchResponse> branchResponse = branchService.getBranchesByItemName(itemName);
        return ResponseBuilder.success(HttpStatus.OK,"Branches retrieved successfully!",branchResponse);
    }
}

