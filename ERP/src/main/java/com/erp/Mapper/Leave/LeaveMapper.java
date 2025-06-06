package com.erp.Mapper.Leave;

import com.erp.Dto.Request.LeaveRequest;
import com.erp.Dto.Response.LeaveResponse;
import com.erp.Model.Leave;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface LeaveMapper {
    Leave mapToLeave(LeaveRequest dto);
    void updateLeave(@MappingTarget Leave entity, LeaveRequest dto);
    @Mapping(source = "user", target = "user")
    LeaveResponse mapToLeaveResponse(Leave leave);
    List<LeaveResponse> mapToLeaveResponseList(List<Leave> leaveList);
}


