package com.erp.Dto.Response;

import com.erp.Model.Role;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserResponse {

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private long phoneNo;

    private boolean isActive;

    private LocalDate createdAt;

    private LocalDate lastModifiedAt;

    private List<String> roleNames;

}
