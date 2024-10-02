package com.yogesh.scalermsprojectyogesh.family.repository;

import com.yogesh.scalermsprojectyogesh.family.model.entity.UserFund;
import com.yogesh.scalermsprojectyogesh.user.model.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserFundRepository extends JpaRepository<UserFund, Long> {
    Optional<UserFund> findByUserId(Long userId);
}
