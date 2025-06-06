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
import com.erp.Repository.Salary.SalaryRepository;
import com.erp.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;
    private final UserRepository userRepository;
    private final SalaryMapper salaryMapper;

    @Override
    public SalaryResponse generateSalaryForMonth(SalaryRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User Not Found! Invalid Id: " + request.getUserId()));

        YearMonth month = request.getMonth();
        int workingDays = month.lengthOfMonth();
        int paidDays = Optional.ofNullable(request.getPaidDays()).orElse(workingDays);
        double bonus = Optional.ofNullable(request.getBonus()).orElse(0.0);
        double baseSalary = request.getBaseSalary();
        double deductions = Optional.ofNullable(request.getDeductions())
                .orElse(baseSalary / workingDays * (workingDays - paidDays));
        double netSalary = Math.max(0, (baseSalary * paidDays / workingDays) - deductions + bonus);

        Salary salary = salaryRepository.findByUserIdAndMonth(request.getUserId(), month).orElse(new Salary());

        salary.setUser(user);
        salary.setMonth(month);
        salary.setBaseSalary(baseSalary);
        salary.setWorkingDays(workingDays);
        salary.setPaidDays(paidDays);
        salary.setDeductions(deductions);
        salary.setBonus(bonus);
        salary.setNetSalary(netSalary);
        salary.setRemarks(request.getRemarks());
        salary.setAmountStatus(AmountStatus.PENDING);

        salaryRepository.save(salary);
        return salaryMapper.mapToResponse(salary);
    }

    @Override
    public SalaryResponse updateSalary(SalaryRequest request) {
        Salary salary = salaryRepository.findByUserIdAndMonth(request.getUserId(), request.getMonth())
                .orElseThrow(() -> new SalaryNotFoundException("Salary Not Found! Invalid User Id: "
                        + request.getUserId() + ", Month: " + request.getMonth()));

        salary.setBaseSalary(request.getBaseSalary());
        salary.setBonus(Optional.ofNullable(request.getBonus()).orElse(0.0));
        salary.setDeductions(Optional.ofNullable(request.getDeductions()).orElse(0.0));
        salary.setRemarks(request.getRemarks());
        salary.setPaidDays(Optional.ofNullable(request.getPaidDays()).orElse(salary.getPaidDays()));
        salary.setNetSalary(Math.max(0, salary.getBaseSalary() - salary.getDeductions() + salary.getBonus()));
        salaryRepository.save(salary);
        return salaryMapper.mapToResponse(salary);
    }

    @Override
    public SalaryResponse markSalaryAsPaid(SalaryRequest request) {
        Salary salary = salaryRepository.findByUserIdAndMonth(request.getUserId(), request.getMonth())
                .orElseThrow(() -> new SalaryNotFoundException("Salary Not Found! Invalid User Id: "
                        + request.getUserId() + ", Month: " + request.getMonth()));

        salary.setAmountStatus(AmountStatus.PAID);
        salary.setPaymentDate(YearMonth.now());
        salaryRepository.save(salary);
        return salaryMapper.mapToResponse(salary);
    }

    @Override
    public SalaryResponse getSalaryByUserAndMonth(SalaryRequest request) {
        Salary salary = salaryRepository.findByUserIdAndMonth(request.getUserId(), request.getMonth())
                .orElseThrow(() -> new SalaryNotFoundException("Salary Not Found! Invalid User Id: "
                        + request.getUserId() + ", Month: " + request.getMonth()));

        return salaryMapper.mapToResponse(salary);
    }

    @Override
    public List<SalaryResponse> getSalariesByUserId(Param param) {
        List<Salary> salaries = salaryRepository.findByUserId(param.getUserId());
        if (salaries.isEmpty())
            throw new SalaryNotFoundException("Salary Records Not Found! Invalid User Id: " + param.getUserId());

        return salaries.stream().map(salaryMapper::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<SalaryResponse> getSalariesByMonth(SalaryRequest request) {
        List<Salary> salaries = salaryRepository.findByMonth(request.getMonth());
        if (salaries.isEmpty())
            throw new SalaryNotFoundException("Salary Records Not Found! Invalid Month: " + request.getMonth());

        return salaries.stream().map(salaryMapper::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<SalaryResponse> getAllSalaries(int page, int size) {
        List<Salary> salaries = salaryRepository.findAll();
        if (salaries.isEmpty())
            throw new SalaryNotFoundException("Salary Records Not Found!");

        return salaries.stream().map(salaryMapper::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public SalaryResponse deleteSalaryByUserAndMonth(SalaryRequest request) {
        Salary salary = salaryRepository.findByUserIdAndMonth(request.getUserId(), request.getMonth())
                .orElseThrow(() -> new SalaryNotFoundException("Salary Not Found! Invalid User Id: "
                        + request.getUserId() + ", Month: " + request.getMonth()));

        salaryRepository.delete(salary);
        return salaryMapper.mapToResponse(salary);
    }

    // PRIVATE HELPER
    private Salary findByUserIdAndMonth(Long userId, YearMonth month) {
        return salaryRepository.findByUserIdAndMonth(userId, month)
                .orElseThrow(() -> new SalaryNotFoundException(
                        "Salary Not Found! Invalid User ID: " + userId + " or Month: " + month));
    }
}
