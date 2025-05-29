package com.erp.Repository.Admin;

import com.erp.Model.Admin;
import com.erp.Repository.GenericUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("adminUserRepository")
public interface AdminUserRepository extends JpaRepository<Admin, Long>, GenericUserRepository<Admin> {
    Optional<Admin> findByEmail(String email);
}
