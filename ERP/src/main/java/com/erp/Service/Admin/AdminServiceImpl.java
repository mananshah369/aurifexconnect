package com.erp.Service.Admin;

import com.erp.Dto.Request.AdminRequest;
import com.erp.Dto.Response.AdminResponse;
import com.erp.Mapper.Admin.AdminMapper;
import com.erp.Model.Admin;
import com.erp.Model.RootUser;
import com.erp.Repository.Admin.AdminRepository;
import com.erp.Repository.Rootuser.RootUserRepository;
import com.erp.Security.util.UserIdentity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final UserIdentity userIdentity;
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final RootUserRepository rootUserRepository;

    @Override
    public AdminResponse createAdmin(AdminRequest adminRequest) {

        RootUser rootUser = userIdentity.getCurrentUser();

        Admin admin = adminMapper.mapTAdmin(adminRequest);
        admin.setCreatedByRootUserId(rootUser.getId());

        adminRepository.save(admin);

        return adminMapper.mapToAdminResponse(admin);
    }
}
