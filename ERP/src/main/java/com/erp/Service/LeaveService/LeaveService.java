package com.erp.Service.LeaveService;

import com.erp.Dto.Request.LeaveRequest;
import com.erp.Dto.Request.Param;
import com.erp.Dto.Response.LeaveResponse;

import java.util.List;

public interface LeaveService {

    LeaveResponse createLeaveRequest(LeaveRequest request);

    LeaveResponse getLeaveRequestsByUserId(Param param);

    LeaveResponse updateLeaveStatus(LeaveRequest request);

    List<LeaveResponse> getAllLeaveRequests();

    LeaveResponse updateLeaveRequestByLeaveId(LeaveRequest request);

    List<LeaveResponse> getLeaveRequestsByStatus(LeaveRequest request);

    List<LeaveResponse> getLeaveRequestsByDateRange(LeaveRequest request);

    LeaveResponse deleteLeaveRequest(Param param);
}
