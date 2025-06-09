package com.erp.Service.SalaryService;

import com.erp.Dto.Request.Param;
import com.erp.Dto.Request.SalaryRequest;
import com.erp.Dto.Response.SalaryResponse;
import com.erp.Enum.AmountStatus;
import com.erp.Exception.Salary.SalaryNotFoundException;
import com.erp.Exception.User.UserNotFoundException;
import com.erp.Mapper.Salary.SalaryMapper;
import com.erp.Model.Salary;
import com.erp.Model.User;
import com.erp.Repository.Attendance.AttendanceRepository;
import com.erp.Repository.Salary.SalaryRepository;
import com.erp.Repository.User.UserRepository;
import com.erp.Service.Attendance.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {

    private final AttendanceService attendanceService;
    private final SalaryRepository salaryRepository;
    private final UserRepository userRepository;
    private final SalaryMapper salaryMapper;
    private final AttendanceRepository attendanceRepository;

    @Override
    public SalaryResponse generateSalaryForMonth(SalaryRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User Not Found! Invalid Id: " + request.getUserId()));
        YearMonth month = request.getMonth();
        int workingDays = Optional.ofNullable(request.getWorkingDays()).orElse(month.lengthOfMonth());
        int paidDays = Optional.ofNullable(request.getPaidDays())
                .orElse(attendanceService.countPresentDaysByUserIdAndMonth(request.getUserId(), month));
        long baseSalary = request.getBaseSalary();
        long bonus = request.getBonus();
        long deductions = request.getDeductions();
        if (deductions == 0) {
            deductions = (baseSalary / workingDays) * (workingDays - paidDays);
        }
        long netSalary = Math.max(0L, (baseSalary * paidDays / workingDays) - deductions + bonus);

        Optional<Salary> optionalSalary = salaryRepository.findByUserIdAndMonth(request.getUserId(), month);

        if (optionalSalary.isPresent()) {
            Salary existing = optionalSalary.get();
            if (existing.getAmountStatus() == AmountStatus.PAID) {
                return salaryMapper.mapToResponse(existing);
            }
        }
        Salary salary = optionalSalary.orElse(new Salary());
        salary.setUser(user);
        salary.setMonth(month);
        salary.setBaseSalary(baseSalary);
        salary.setWorkingDays(workingDays);
        salary.setPaidDays(paidDays);
        salary.setDeductions(deductions);
        salary.setBonus(bonus);
        salary.setNetSalary(netSalary);
        salary.setRemarks(request.getRemarks());

        if (salary.getAmountStatus() == null) {
            salary.setAmountStatus(AmountStatus.PENDING);
        }
        salaryRepository.save(salary);
        return salaryMapper.mapToResponse(salary);
    }



    @Override
    public SalaryResponse getSalaryByUserAndMonth(SalaryRequest request) {
        Salary salary = findByUserIdAndMonth(request.getUserId(), request.getMonth());
        return salaryMapper.mapToResponse(salary);
    }

    @Override
    public List<SalaryResponse> getSalariesByUserId(Param param) {
        List<Salary> salaries = salaryRepository.findByUserId(param.getUserId());
        if (salaries.isEmpty()) {
            throw new SalaryNotFoundException("Salary Records Not Found! Invalid User Id: " + param.getUserId());
        }
        return salaries.stream()
                .map(salaryMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SalaryResponse> getSalariesByMonth(SalaryRequest request) {
        List<Salary> salaries = salaryRepository.findByMonth(request.getMonth());
        if (salaries.isEmpty()) {
            throw new SalaryNotFoundException("Salary Records Not Found! Invalid Month: " + request.getMonth());
        }
        return salaries.stream()
                .map(salaryMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SalaryResponse> getAllSalaries(int page, int size) {
        List<Salary> salaries = salaryRepository.findAll();
        if (salaries.isEmpty()) {
            throw new SalaryNotFoundException("Salary Records Not Found!");
        }
        return salaries.stream()
                .map(salaryMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SalaryResponse updateSalary(SalaryRequest request) {
        Salary salary = findByUserIdAndMonth(request.getUserId(), request.getMonth());

        int paidDays = Optional.ofNullable(request.getPaidDays()).orElse(salary.getPaidDays());
        long baseSalary = request.getBaseSalary();
        long bonus = request.getBonus();
        long deductions = request.getDeductions();
        salary.setBaseSalary(baseSalary);
        salary.setBonus(bonus);
        salary.setDeductions(deductions);
        salary.setRemarks(request.getRemarks());
        salary.setPaidDays(paidDays);
        long netSalary = Math.max(0L, (baseSalary * paidDays / salary.getWorkingDays()) - deductions + bonus);
        salary.setNetSalary(netSalary);

        salaryRepository.save(salary);
        return salaryMapper.mapToResponse(salary);
    }

    @Override
    public SalaryResponse deleteSalaryByUserAndMonth(SalaryRequest request) {
        Salary salary = findByUserIdAndMonth(request.getUserId(), request.getMonth());
        salaryRepository.delete(salary);
        return salaryMapper.mapToResponse(salary);
    }

    @Override
    public SalaryResponse markSalaryAsPaid(SalaryRequest request) {
        Salary salary = findByUserIdAndMonth(request.getUserId(), request.getMonth());
        salary.setAmountStatus(AmountStatus.PAID);
        salary.setPaymentDate(YearMonth.now());
        salaryRepository.save(salary);
        return salaryMapper.mapToResponse(salary);
    }

    private Salary findByUserIdAndMonth(Long userId, YearMonth month) {
        return salaryRepository.findByUserIdAndMonth(userId, month)
                .orElseThrow(() -> new SalaryNotFoundException(
                        "Salary Not Found! Invalid User ID: " + userId + ", Month: " + month));
    }
}