package com.yogesh.scalermsprojectyogesh.user.repository;

import com.yogesh.scalermsprojectyogesh.user.model.entity.UserMaster;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMasterRepository extends JpaRepository<UserMaster, Long> {
//    @EntityGraph(attributePaths = {"roles"})
    Optional<UserMaster> findByUsername(String username);
    Optional<UserMaster> findByEmailId(String emailId);
}
