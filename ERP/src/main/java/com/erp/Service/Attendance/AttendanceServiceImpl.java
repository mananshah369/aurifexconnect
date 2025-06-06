package com.erp.Service.Attendance;

import com.erp.Dto.Request.AttendanceRequest;
import com.erp.Dto.Request.Param;
import com.erp.Dto.Response.AttendanceResponse;
import com.erp.Enum.AttendanceStatus;
import com.erp.Exception.Attendance.AttendanceAlreadyExistsException;
import com.erp.Exception.Attendance.AttendanceInvalidException;
import com.erp.Exception.Attendance.AttendanceNotFoundException;
import com.erp.Exception.User.UserNotFoundException;
import com.erp.Mapper.Attendance.AttendanceMapper;
import com.erp.Model.Attendance;
import com.erp.Model.User;
import com.erp.Repository.Attendance.AttendanceRepository;
import com.erp.Repository.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    private final UserRepository userRepository;
    private final Clock clock;

    @Override
    public AttendanceResponse checkIn(Param param) {
        long userId = param.getUserId();
        LocalDate today = LocalDate.now(clock);
        attendanceRepository.findTopByUser_IdAndCheckOutIsNullOrderByDateDesc(userId)
                .filter(a -> a.getDate().isBefore(today))
                .filter(a -> Duration.between(a.getCheckIn(), LocalDateTime.now(clock)).toHours() >= 8)
                .ifPresent(att -> {
                    att.setCheckOut(att.getCheckIn().plusHours(8));
                    calculateWorkingDetails(att);
                    attendanceRepository.save(att);
                });
        attendanceRepository.findByUser_IdAndDate(userId, today)
                .ifPresent(a -> { throw new AttendanceAlreadyExistsException("Already checked in today."); });
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setDate(today);
        attendance.setCheckIn(LocalDateTime.now(clock));
        attendance.setStatus(AttendanceStatus.PRESENT);
        attendance.setWorkingHours(0.0);
        attendance.setWorkingDays(0.0);
        attendanceRepository.save(attendance);
        return attendanceMapper.mapToResponse(attendance);
    }

    @Override
    public AttendanceResponse checkOut(Param param) {
        Attendance attendance = attendanceRepository.findByUser_IdAndDate(param.getUserId(), LocalDate.now(clock))
                .orElseThrow(() -> new AttendanceNotFoundException("Check-in record not found for today."));
        if (attendance.getCheckOut() != null) {
            throw new AttendanceAlreadyExistsException("Already checked out today.");
        }
        LocalDateTime checkOutTime = LocalDateTime.now(clock);
        if (checkOutTime.isBefore(attendance.getCheckIn())) {
            throw new AttendanceInvalidException("Check-out before check-in.");
        }
        attendance.setCheckOut(checkOutTime);
        calculateWorkingDetails(attendance);
        attendanceRepository.save(attendance);
        AttendanceResponse response = attendanceMapper.mapToResponse(attendance);
        response.setWorkingHours(formatHours(response.getWorkingHours()));
        response.setWorkingDays(formatDays(response.getWorkingDays()));
        return response;
    }

    @Override
    public AttendanceResponse updateAttendance(AttendanceRequest request) {
        Attendance attendance = attendanceRepository.findByUser_IdAndDate(request.getUserId(), request.getDate())
                .orElseThrow(() -> new AttendanceNotFoundException("Attendance not found for user and date."));

        if (request.getCheckInTime() != null) attendance.setCheckIn(request.getCheckInTime());
        if (request.getCheckOutTime() != null) attendance.setCheckOut(request.getCheckOutTime());

        calculateWorkingDetails(attendance);
        attendanceRepository.save(attendance);

        return attendanceMapper.mapToResponse(attendance);
    }

    @Override
    public AttendanceResponse getAttendanceById(Param param) {
        Attendance attendance = attendanceRepository.findById(param.getId())
                .orElseThrow(() -> new AttendanceNotFoundException("Attendance not found."));
        return attendanceMapper.mapToResponse(attendance);
    }

    @Override
    public List<AttendanceResponse> getByDate(LocalDate date) {
        List<Attendance> attendances = attendanceRepository.findByDate(date);
        if (attendances.isEmpty()) {
            throw new AttendanceNotFoundException("No attendance records found for the given date.");
        }
        return attendances.stream()
                .map(attendanceMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceResponse> getByUserId(Param param) {
        List<Attendance> attendances = attendanceRepository.findByUser_Id(param.getUserId());
        if (attendances.isEmpty()) {
            throw new AttendanceNotFoundException("No attendance records found for the user.");
        }
        return attendances.stream()
                .map(attendanceMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceResponse> getAllAttendances() {
        List<Attendance> attendances = attendanceRepository.findAll();
        if (attendances.isEmpty()) {
            throw new AttendanceNotFoundException("No attendance records found.");
        }
        return attendances.stream()
                .map(attendanceMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceResponse> getMonthlyReport(AttendanceRequest request) {
        if (request.getDate() == null) {
            throw new IllegalArgumentException("Date must be provided for monthly report.");
        }
        YearMonth month = YearMonth.from(request.getDate());
        LocalDate start = month.atDay(1);
        LocalDate end = month.atEndOfMonth();

        List<Attendance> attendances = attendanceRepository.findByUser_IdAndDateBetween(request.getUserId(), start, end);

        return attendances.stream()
                .map(attendanceMapper::mapToResponse)
                .sorted(Comparator.comparing(r -> r.getCheckIn() != null ? r.getCheckIn() : LocalDateTime.MIN))
                .collect(Collectors.toList());
    }

    @Override
    public AttendanceResponse deleteAttendanceByUserIDandDate(AttendanceRequest request) {
        Attendance attendance = attendanceRepository.findByUser_IdAndDate(request.getUserId(), request.getDate())
                .orElseThrow(() -> new AttendanceNotFoundException("No attendance found for given user and date"));
        attendanceRepository.delete(attendance);
        return attendanceMapper.mapToResponse(attendance);
    }

    @Override
    public AttendanceResponse deleteAllAttendances(AttendanceRequest request) {
        List<Attendance> attendances = attendanceRepository.findByUser_Id(request.getUserId());
        if (attendances.isEmpty()) {
            throw new AttendanceNotFoundException("No attendance records for user.");
        }
        attendanceRepository.deleteAll(attendances);
        return attendanceMapper.mapToResponse(attendances.get(attendances.size() - 1));
    }

    @Override
    public int countPresentDaysByUserIdAndMonth(Long userId, YearMonth month) {
        LocalDate startDate = month.atDay(1);
        LocalDate endDate = month.atEndOfMonth();
        List<Attendance> attendanceList = attendanceRepository.findByUser_IdAndDateBetween(userId, startDate, endDate);
        long presentDays = attendanceList.stream()
                .filter(att -> att.getCheckOut() != null)
                .map(Attendance::getDate)
                .distinct()
                .count();
        return (int) presentDays;
    }

    // Clean and shortened working hours and days calculator
    private void calculateWorkingDetails(Attendance a) {
        double hours = (a.getCheckIn() != null && a.getCheckOut() != null)
                ? Duration.between(a.getCheckIn(), a.getCheckOut()).toMinutes() / 60.0
                : 0.0;
        a.setWorkingHours(hours);
        a.setWorkingDays(hours == 0 ? 0.0 : hours >= 8 ? 1.0 : 0.5);
    }

    private String formatHours(String value) {
        double hours = Double.parseDouble(value);
        int h = (int) hours;
        int m = (int) Math.round((hours - h) * 60);
        if (h == 0 && m == 0) return "0 minutes";
        if (h == 0) return m + " minutes";
        if (m == 0) return h + " hours";
        return h + " hours " + m + " minutes";
    }

    private String formatDays(String value) {
        double days = Double.parseDouble(value);
        if (days == 1.0) return "1 day";
        if (days == 0.5) return "Half day";
        return days + " days";
    }

    @Override
    public void autoCheckout() {
        LocalDate today = LocalDate.now(clock);
        List<Attendance> pendingCheckouts =
                attendanceRepository.findByDateAndCheckOutIsNull(today);
        for (Attendance attendance : pendingCheckouts) {
            LocalDateTime checkIn = attendance.getCheckIn();
            LocalDateTime autoCheckOut = checkIn.plusHours(8); // Assume 8 working hours
            attendance.setCheckOut(autoCheckOut);
            attendance.setWorkingHours(8.0);
            attendance.setWorkingDays(1.0);
            attendanceRepository.save(attendance);
        }

    }
}