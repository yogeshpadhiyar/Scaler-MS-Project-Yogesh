package com.yogesh.scalermsprojectyogesh.transaction.repository;

import com.yogesh.scalermsprojectyogesh.transaction.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<List<Transaction>> findByUserId(Long userId) throws Exception;

    Optional<List<Transaction>> findByCategoryId(Long categoryId) throws Exception;
}
