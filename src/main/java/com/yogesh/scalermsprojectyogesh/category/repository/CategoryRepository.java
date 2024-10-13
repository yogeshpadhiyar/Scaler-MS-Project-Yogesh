package com.yogesh.scalermsprojectyogesh.category.repository;

import com.yogesh.scalermsprojectyogesh.category.model.CategoryBean;
import com.yogesh.scalermsprojectyogesh.category.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUserIdAndActive(Long userId, Boolean active);

    List<Category> findByFamilyIdAndActive(Long familyId, Boolean active);
}
