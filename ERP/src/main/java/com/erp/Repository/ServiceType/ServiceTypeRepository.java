package com.erp.Repository.ServiceType;

import com.erp.Dto.Response.ServiceTypeResponse;
import com.erp.Model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceTypeRepository extends JpaRepository<ServiceType,Long> {

    List<ServiceType> findByServiceName(String name);
}
