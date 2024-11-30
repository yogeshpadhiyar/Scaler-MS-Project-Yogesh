package com.yogesh.scalermsprojectyogesh.user.model.entity;

import com.yogesh.scalermsprojectyogesh.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.HashSet;
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

    @ManyToMany(mappedBy = "roles")
    private Collection<UserMaster> users;
}
