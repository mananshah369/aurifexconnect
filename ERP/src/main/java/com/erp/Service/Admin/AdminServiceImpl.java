package com.erp.Service.Admin;

import com.erp.Dto.Request.AdminRequest;
import com.erp.Dto.Response.AdminResponse;
import com.erp.Exception.Admin.AdminNotFoundException;
import com.erp.Mapper.Admin.AdminMapper;
import com.erp.Model.Admin;
import com.erp.Model.RootUser;
import com.erp.Repository.Admin.AdminUserRepository;
import com.erp.Repository.Rootuser.RootUserRepository;
import com.erp.Security.util.UserIdentity;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserIdentity userIdentity;
    private final AdminUserRepository adminRepository;
    private final AdminMapper adminMapper;
    private final RootUserRepository rootUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @PreAuthorize("hasAuthority('ROLE_ROOT')")
    public AdminResponse createAdmin(AdminRequest adminRequest) {
        // Get the current authenticated RootUser
        RootUser currentUser = (RootUser) userIdentity.getCurrentUser();

        Admin admin = adminMapper.mapTAdmin(adminRequest);
        admin.setCreatedByRootUserId(currentUser.getId());
        admin.setLastUpdatedByRootUserId(currentUser.getId());
        admin.setPassword(passwordEncoder.encode(adminRequest.getPassword()));

        adminRepository.save(admin); // No ambiguity after removing save from GenericUserRepository

        return adminMapper.mapToAdminResponse(admin);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_ROOT')")
    public List<AdminResponse> getListOfAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return adminMapper.mapToListOfAdminResponse(admins);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_ROOT')")
    public AdminResponse updateAdminById(AdminRequest adminRequest, long adminId) {
        // Get the current authenticated RootUser
        RootUser currentUser = (RootUser) userIdentity.getCurrentUser();

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminNotFoundException("Invalid ID: " + adminId + " ,admin not found !"));

        adminMapper.mapToAdminEntity(adminRequest, admin);
        admin.setLastUpdatedByRootUserId(currentUser.getId());
        rootUserRepository.save(currentUser);
        adminRepository.save(admin); // No ambiguity after removing save from GenericUserRepository

        return adminMapper.mapToAdminResponse(admin);
    }

    @Override
    public AdminResponse deleteAdminById(long id) {
        RootUser currentUser = (RootUser) userIdentity.getCurrentUser();
        if (currentUser == null) {
            throw new SecurityException("No authenticated user found");
        }

        Admin admin = adminRepository.findById(id)
                .orElseThrow(()-> new AdminNotFoundException("Admin not found with this id: "+id));

        admin.set_Active(false);
        adminRepository.save(admin);

        return adminMapper.mapToAdminResponse(admin);
    }
}