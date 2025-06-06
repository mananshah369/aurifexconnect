package com.erp.Model;

import com.erp.Enum.AttendanceStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @CreatedDate
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "working_hours")
    private Double workingHours;

    @Column(name = "working_days")
    private Double workingDays;

    @Column(name = "check_in")
    private LocalDateTime checkIn;

    @Column(name = "check_out")
    private LocalDateTime checkOut;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
}