package com.yogesh.scalermsprojectyogesh.user.repository;

import com.yogesh.scalermsprojectyogesh.user.model.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMasterRepository extends JpaRepository<UserMaster, Long> {
    Optional<UserMaster> findByUsername(String username);
}
