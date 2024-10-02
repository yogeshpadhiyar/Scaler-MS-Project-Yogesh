package com.yogesh.scalermsprojectyogesh.family.repository;

import com.yogesh.scalermsprojectyogesh.family.model.entity.FamilyMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface FamilyMasterRepository extends JpaRepository<FamilyMaster, Long> {
    Optional<FamilyMaster> findByFamilyName(String familyName);

    @Transactional
    @Modifying
    @Query(value = "Update FamilyMaster fm set fm.familyFund = :familyFund, fm.availableFamilyFund = :availableFamilyFund where fm.id = :id")
    void updateById(@Param(value = "id") Long id, @Param(value = "familyFund") BigDecimal familyFund, @Param(value = "availableFamilyFund") BigDecimal availableFamilyFund);
}
