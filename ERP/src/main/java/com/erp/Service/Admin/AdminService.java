package com.erp.Service.Admin;

import com.erp.Dto.Request.AdminRequest;
import com.erp.Dto.Response.AdminResponse;

public interface AdminService {

    AdminResponse createAdmin(AdminRequest adminRequest);
}
