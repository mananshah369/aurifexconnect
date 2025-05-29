package com.erp.Service.Master;

import com.erp.Dto.Request.MasterRequest;
import com.erp.Dto.Response.MasterResponse;

public interface MasterService {

    MasterResponse createMaster(MasterRequest masterRequest , long ledgerId);

    MasterResponse findById(long masterId);

//    MasterResponse updateMaster(Long masterId, MasterRequest masterRequest);
//
//    MasterResponse deleteMaster(Long masterId);
}
