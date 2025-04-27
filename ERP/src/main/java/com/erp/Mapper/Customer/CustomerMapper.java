package com.erp.Mapper.Customer;

import com.erp.Dto.Request.CustomerRequest;
import com.erp.Dto.Response.CustomerResponse;
import com.erp.Model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CustomerMapper {

    Customer maptoCustomer(CustomerRequest customerRequest);

    void mapToCustomerEntity(CustomerRequest customerRequest, @MappingTarget Customer customer);

    CustomerResponse mapToCustomerResponse(Customer customer);
}
