package com.erp.Repository.Admin;

import com.erp.Model.Admin;
import com.erp.Model.RootUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<RootUser> findByEmail(String email);
    boolean existsByEmail(String email);
}
