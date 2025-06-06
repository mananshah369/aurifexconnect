package com.erp.Repository.Attendance;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.erp.Model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByUser_IdAndDate(Long userId, LocalDate date);
    List<Attendance> findByUser_Id(Long userId);
    List<Attendance> findByDate(LocalDate date);
    List<Attendance> findByUser_IdAndDateBetween(Long userId,
                                                 LocalDate startDate,
                                                 LocalDate endDate);
}