package com.erp.Repository.Salary;

import com.erp.Model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
    Optional<Salary> findByUserIdAndMonth(Long userId, YearMonth month);
    List<Salary> findByUserId(Long userId);
    List<Salary> findByMonth(YearMonth month);
}
