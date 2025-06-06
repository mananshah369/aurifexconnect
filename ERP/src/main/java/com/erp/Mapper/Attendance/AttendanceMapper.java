package com.erp.Mapper.Attendance;

import com.erp.Dto.Request.AttendanceRequest;
import com.erp.Dto.Response.AttendanceResponse;
import com.erp.Model.Attendance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {
    Attendance mapToEntity(AttendanceRequest attendanceRequest);
    void updateEntityFromRequest(AttendanceRequest dto, @MappingTarget Attendance entity);
    AttendanceResponse mapToResponse(Attendance attendance);
    List<AttendanceResponse> mapToResponseList(List<Attendance> attendances);

}
