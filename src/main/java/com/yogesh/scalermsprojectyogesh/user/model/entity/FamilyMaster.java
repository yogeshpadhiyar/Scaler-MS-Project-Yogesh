package com.yogesh.scalermsprojectyogesh.user.model.entity;

import com.yogesh.scalermsprojectyogesh.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FamilyMaster extends BaseModel {

    @Column(nullable = false)
    private String familyName;

    // Reference to the parent user (self-referencing Many-to-One)
    @OneToMany(mappedBy = "family",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<UserMaster> users;
}
