package com.erp.Service.LeaveService;

import com.erp.Dto.Request.LeaveRequest;
import com.erp.Dto.Request.Param;
import com.erp.Dto.Response.LeaveResponse;
import com.erp.Enum.LeaveStatus;
import com.erp.Enum.LeaveType;
import com.erp.Exception.Leave.LeaveNotFoundException;
import com.erp.Exception.User.UserNotFoundException;
import com.erp.Mapper.Leave.LeaveMapper;
import com.erp.Model.Leave;
import com.erp.Model.User;
import com.erp.Repository.Leave.LeaveRepository;
import com.erp.Repository.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;
    private final LeaveMapper leaveMapper;
    private final UserRepository userRepository;

    @Override
    public LeaveResponse createLeaveRequest(LeaveRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + request.getUserId()));

        Leave leave = new Leave();
        leave.setUser(user);
        leave.setStartDate(request.getStartDate());
        leave.setEndDate(request.getEndDate());
        leave.setReason(request.getReason());
        leave.setLeaveType(parseLeaveType(request.getLeaveType()));
        leave.setStatus(LeaveStatus.PENDING);
        leaveRepository.save(leave);
        return leaveMapper.mapToLeaveResponse(leave);
    }

    @Override
    public LeaveResponse getLeaveRequestsByUserId(Param param) {
        Leave leave = leaveRepository.findById(param.getUserId())
                .orElseThrow(() -> new LeaveNotFoundException("Leave not found with id: " + param.getUserId()));
        return leaveMapper.mapToLeaveResponse(leave);
    }

    @Override
    public LeaveResponse updateLeaveStatus(LeaveRequest request) {
        Leave leave = leaveRepository.findById(request.getId())
                .orElseThrow(() -> new LeaveNotFoundException("Leave not found with id: " + request.getId()));

        if (request.getStatus() == null)
            throw new IllegalArgumentException("Leave status must not be null");

        leave.setStatus(request.getStatus());
        leaveRepository.save(leave);
        return leaveMapper.mapToLeaveResponse(leave);
    }

    @Override
    public List<LeaveResponse> getAllLeaveRequests() {
        List<Leave> leaves = leaveRepository.findAll();
        if (leaves.isEmpty())
            throw new LeaveNotFoundException("No leave records found");

        return leaves.stream()
                .map(leaveMapper::mapToLeaveResponse)
                .collect(Collectors.toList());
    }

    @Override
    public LeaveResponse updateLeaveRequestByLeaveId(LeaveRequest request) {
        Leave leave = leaveRepository.findById(request.getId())
                .orElseThrow(() -> new LeaveNotFoundException("Leave not found with id: " + request.getId()));

        leave.setStartDate(request.getStartDate());
        leave.setEndDate(request.getEndDate());
        leave.setReason(request.getReason());
        leave.setLeaveType(parseLeaveType(request.getLeaveType()));
        leaveRepository.save(leave);
        return leaveMapper.mapToLeaveResponse(leave);
    }

    @Override
    public List<LeaveResponse> getLeaveRequestsByStatus(LeaveRequest request) {
        if (request.getStatus() == null)
            throw new IllegalArgumentException("Leave status must not be null");

        List<Leave> leaves = leaveRepository.findByStatus(request.getStatus());
        if (leaves.isEmpty())
            throw new LeaveNotFoundException("No leave records found with status: " + request.getStatus());

        return leaves.stream()
                .map(leaveMapper::mapToLeaveResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<LeaveResponse> getLeaveRequestsByDateRange(LeaveRequest request) {
        LocalDate start = request.getStartDate();
        LocalDate end = request.getEndDate();

        if (start == null || end == null)
            throw new IllegalArgumentException("Start date and end date must not be null");

        List<Leave> leaves = leaveRepository.findByStartDateBetween(start, end);
        if (leaves.isEmpty())
            throw new LeaveNotFoundException("No leave records found between " + start + " and " + end);

        return leaves.stream()
                .map(leaveMapper::mapToLeaveResponse)
                .collect(Collectors.toList());
    }

    @Override
    public LeaveResponse deleteLeaveRequest(Param param) {
        Leave leave = leaveRepository.findById(param.getId())
                .orElseThrow(() -> new LeaveNotFoundException("Leave not found with id: " + param.getId()));

        leaveRepository.delete(leave);
        return leaveMapper.mapToLeaveResponse(leave);
    }

    // Helper method to parse LeaveType from string safely
    private LeaveType parseLeaveType(String type) {
        if (type == null || type.isBlank()) {
            return LeaveType.UNPAID;
        }
        try {
            return LeaveType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Return UNPAID instead of throwing exception for invalid value
            return LeaveType.UNPAID;
        }
    }
}
