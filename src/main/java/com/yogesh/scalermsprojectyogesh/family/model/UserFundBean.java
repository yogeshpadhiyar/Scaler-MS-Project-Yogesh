package com.yogesh.scalermsprojectyogesh.family.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yogesh.scalermsprojectyogesh.family.model.entity.FamilyMaster;
import com.yogesh.scalermsprojectyogesh.family.model.entity.UserFund;
import com.yogesh.scalermsprojectyogesh.service.RequestMapper;
import com.yogesh.scalermsprojectyogesh.user.model.UserMasterBean;
import com.yogesh.scalermsprojectyogesh.user.model.entity.UserMaster;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserFundBean implements RequestMapper<UserFund> {

    private long userId;
    private long fundAssignerUserId;
    private long familyId;
    private BigDecimal totalFundAmount;
    private BigDecimal availableAmount;

    @Override
    public UserFund createEntityBean() {
        return UserFund.builder()
                .userId(userId)
                .fundAssignerUserId(fundAssignerUserId)
                .familyId(familyId)
                .totalFundAmount(totalFundAmount)
                .build();
    }
}
