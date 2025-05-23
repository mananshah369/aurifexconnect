package com.erp.Repository.User;

import com.erp.Model.RootUser;
import com.erp.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<RootUser> findByEmail(String email);
    boolean existsByEmail(String email);

}
