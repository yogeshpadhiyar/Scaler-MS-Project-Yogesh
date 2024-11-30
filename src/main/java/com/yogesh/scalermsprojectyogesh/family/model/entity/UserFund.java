package com.yogesh.scalermsprojectyogesh.family.model.entity;

import com.yogesh.scalermsprojectyogesh.family.model.UserFundBean;
import com.yogesh.scalermsprojectyogesh.model.BaseModel;
import com.yogesh.scalermsprojectyogesh.service.ResponseMapper;
import com.yogesh.scalermsprojectyogesh.user.model.entity.UserMaster;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_fund")
public class UserFund extends BaseModel implements ResponseMapper<UserFundBean> {

//    @OneToOne(cascade=CascadeType.ALL)
//    @JoinColumn(name = "user_id", nullable = false)
    private Long userId;

//    @OneToOne(cascade=CascadeType.ALL)
//    @JoinColumn(name = "fund_assigner_id", nullable = false)
    private Long fundAssignerUserId;

//    @OneToOne(cascade=CascadeType.ALL)
//    @JoinColumn(name = "family_id", nullable = false)
    private Long familyId;

    private BigDecimal totalFundAmount;

    private BigDecimal availableAmount;

    @Override
    public UserFundBean createResponseBean() {
        return UserFundBean.builder()
                .userId(userId)
                .fundAssignerUserId(fundAssignerUserId)
                .familyId(familyId)
                .totalFundAmount(totalFundAmount)
                .availableAmount(availableAmount)
                .build();
    }
}
