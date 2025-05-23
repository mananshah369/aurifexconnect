package com.erp.Mapper.Admin;

import com.erp.Dto.Request.AdminRequest;
import com.erp.Dto.Response.AdminResponse;
import com.erp.Model.Admin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    Admin mapTAdmin (AdminRequest adminRequest);

    AdminResponse mapToAdminResponse (Admin admin);
}
