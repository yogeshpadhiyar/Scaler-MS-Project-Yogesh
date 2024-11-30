package com.yogesh.scalermsprojectyogesh.family.repository;

import com.yogesh.scalermsprojectyogesh.family.model.entity.UserFund;
import com.yogesh.scalermsprojectyogesh.user.model.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserFundRepository extends JpaRepository<UserFund, Long> {
    Optional<UserFund> findByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE UserFund uf set uf.availableAmount = :availableAmount where uf.userId = :userId")
    Integer updateAvailableAmountByUserId(@Param(value = "userId") Long userId, @Param(value = "availableAmount") BigDecimal availableAmount);
}
