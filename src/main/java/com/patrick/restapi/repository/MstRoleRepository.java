package com.patrick.restapi.repository;


import com.patrick.restapi.entity.MstRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MstRoleRepository extends JpaRepository<MstRoleEntity , Integer> {
    Optional<MstRoleEntity> findByRoleAccess(String Role);
}
