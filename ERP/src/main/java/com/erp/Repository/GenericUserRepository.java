package com.erp.Repository;

import com.erp.Model.GenericUser;

import java.util.Optional;

public interface GenericUserRepository<T extends GenericUser> {
    Optional<T> findByEmail(String email);
   // T save(T user);
}
