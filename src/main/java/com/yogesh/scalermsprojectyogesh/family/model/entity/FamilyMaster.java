package com.yogesh.scalermsprojectyogesh.family.model.entity;

import com.yogesh.scalermsprojectyogesh.family.model.FamilyMasterBean;
import com.yogesh.scalermsprojectyogesh.model.BaseModel;
import com.yogesh.scalermsprojectyogesh.service.ResponseMapper;
import com.yogesh.scalermsprojectyogesh.user.model.entity.UserMaster;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "family_master")
public class FamilyMaster extends BaseModel implements ResponseMapper<FamilyMasterBean> {

    @Column(nullable = false)
    private String familyName;

    // Reference to the parent user (self-referencing Many-to-One)
    @OneToMany(mappedBy = "family",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<UserMaster> users;

    private Double familyFund;

    @Override
    public FamilyMasterBean createResponseBean() {
        return FamilyMasterBean.builder()
                .id(this.getId())
                .familyName(familyName)
                .familyMembers(users.stream().map(UserMaster :: createResponseBean).collect(Collectors.toList()))
                .familyFund(familyFund).build();
    }
}
