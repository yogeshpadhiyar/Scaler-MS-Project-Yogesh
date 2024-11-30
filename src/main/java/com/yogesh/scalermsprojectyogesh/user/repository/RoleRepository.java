package com.yogesh.scalermsprojectyogesh.user.repository;

import com.yogesh.scalermsprojectyogesh.user.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> findAllByRoleNameIn(Set<String> roleNames);
}
