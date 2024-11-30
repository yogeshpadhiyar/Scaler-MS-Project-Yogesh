package com.yogesh.scalermsprojectyogesh.user.model.entity;

import com.yogesh.scalermsprojectyogesh.family.model.entity.FamilyMaster;
import com.yogesh.scalermsprojectyogesh.model.BaseModel;
import com.yogesh.scalermsprojectyogesh.service.ResponseMapper;
import com.yogesh.scalermsprojectyogesh.user.model.UserMasterBean;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_master")
public class UserMaster extends BaseModel implements  ResponseMapper<UserMasterBean> {

    private String name;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String emailId;
    private String password;
    private String phone;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Collection<Role> roles;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "family_id")
    private FamilyMaster family;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_user_id")
    private UserMaster parentUser;

    private Boolean isParentOfFamily;

    @Override
    public UserMasterBean createResponseBean() {
        return UserMasterBean.builder()
                .emailId(emailId)
                .name(name)
                .username(username)
                .phone(phone)
                .isParentOfFamily(isParentOfFamily)
                .roles(roles.stream().map(Role::getRoleName).collect(java.util.stream.Collectors.toSet()))
                .build();
    }
}

