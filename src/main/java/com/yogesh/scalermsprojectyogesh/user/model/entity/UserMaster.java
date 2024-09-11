package com.yogesh.scalermsprojectyogesh.user.model.entity;

import com.yogesh.scalermsprojectyogesh.model.BaseModel;
import com.yogesh.scalermsprojectyogesh.service.RequestMapper;
import com.yogesh.scalermsprojectyogesh.service.ResponseMapper;
import com.yogesh.scalermsprojectyogesh.user.model.UserMasterBean;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
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
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_id")
    private FamilyMaster family;
    private Boolean isParentOfFamily;

    @Override
    public UserMasterBean createResponseBean() {
        return UserMasterBean.builder()
                .emailId(emailId)
                .name(name)
                .username(username)
                .phone(phone)
                .isParentOfFamily(isParentOfFamily)
                .build();
    }
}

