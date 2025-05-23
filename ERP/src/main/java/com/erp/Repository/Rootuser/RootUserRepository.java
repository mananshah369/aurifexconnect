package com.erp.Repository;

import com.erp.Model.RootUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RootUserRepository extends JpaRepository<RootUser, Long> {

    Optional<RootUser> findByEmail(String email);
    boolean existsByEmail(String email);
}
