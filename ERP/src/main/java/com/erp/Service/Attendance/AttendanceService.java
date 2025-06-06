package com.erp.Service.Attendance;

import com.erp.Dto.Request.AttendanceRequest;
import com.erp.Dto.Request.Param;
import com.erp.Dto.Response.AttendanceResponse;
import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    AttendanceResponse checkIn(Param param);
    AttendanceResponse checkOut(Param param);
    AttendanceResponse updateAttendance(AttendanceRequest request);
    List<AttendanceResponse> getByDate(LocalDate date);
    AttendanceResponse getAttendanceById(Param param);
    List<AttendanceResponse> getAllAttendances();
    List<AttendanceResponse> getByUserId(Param param);
    List<AttendanceResponse> getMonthlyReport(AttendanceRequest request);
    AttendanceResponse deleteAttendanceByUserIDandDate(AttendanceRequest request);
    AttendanceResponse deleteAllAttendances(AttendanceRequest request);
}
