package com.erp.Mapper.Master;

import com.erp.Dto.Request.MasterRequest;
import com.erp.Dto.Response.MasterResponse;
import com.erp.Model.Master;
import org.springframework.stereotype.Component;

@Component
public class MasterMapperImpl implements MasterMapper {

    @Override
    public Master mapToMasterRequest(MasterRequest masterRequest) {

        if(masterRequest == null){
            return null;
        }

        Master master = new Master();
        master.setAmount(masterRequest.getAmount());
        master.setName(masterRequest.getName());
        master.setDescription(masterRequest.getDescription());
        master.setReferenceType(masterRequest.getReferenceType());
        master.setVoucherType(masterRequest.getVoucherType());
        master.setVoucherIndex(masterRequest.getVoucherIndex());

        return master;
    }

    @Override
    public void mapToMasterEntity(MasterRequest masterRequest, Master master) {

        master.setAmount(masterRequest.getAmount());
        master.setName(masterRequest.getName());
        master.setDescription(masterRequest.getDescription());
        master.setReferenceType(masterRequest.getReferenceType());
        master.setVoucherType(masterRequest.getVoucherType());
        master.setVoucherIndex(masterRequest.getVoucherIndex());

    }

    @Override
    public MasterResponse mapToMasterResponse(Master master) {

        if(master == null){
            return null;
        }

        MasterResponse masterResponse = new MasterResponse();
        masterResponse.setMasterId(master.getMasterId());
        masterResponse.setName(master.getName());
        masterResponse.setAmount(master.getAmount());
        masterResponse.setDescription(master.getDescription());
        masterResponse.setReferenceType(master.getReferenceType());
        masterResponse.setTransactionStatus(master.getTransactionStatus());
        masterResponse.setVoucherType(master.getVoucherType());
        masterResponse.setDate(master.getDate());
        masterResponse.setVoucherIndex(master.getVoucherIndex());

        return masterResponse;
    }
}
