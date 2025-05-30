package com.erp.Repository.User;


import com.erp.Model.User;
import com.erp.Repository.GenericUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long>, GenericUserRepository<User> {
    Optional<User> findByEmail(String email);
}
