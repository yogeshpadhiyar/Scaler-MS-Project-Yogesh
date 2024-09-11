package com.yogesh.scalermsprojectyogesh.user.model.entity;

import com.yogesh.scalermsprojectyogesh.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseModel {

    @Column(nullable = false,unique = true)
    private String roleName;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    private Set<UserMaster> users;
}
