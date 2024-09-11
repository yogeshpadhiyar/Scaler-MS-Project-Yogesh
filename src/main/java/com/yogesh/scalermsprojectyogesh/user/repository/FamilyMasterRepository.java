package com.yogesh.scalermsprojectyogesh.user.repository;

import com.yogesh.scalermsprojectyogesh.user.model.entity.FamilyMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilyMasterRepository extends JpaRepository<FamilyMaster, Long> {
    Optional<FamilyMaster> findByFamilyName(String familyName);
}
