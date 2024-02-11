package com.patrick.restapi.repository;

import com.patrick.restapi.entity.MstUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MstUserRepository extends JpaRepository<MstUserEntity, Integer> {
    Optional<MstUserEntity> findByUsername(String Username);
    boolean existsByUsername(String username);
}
