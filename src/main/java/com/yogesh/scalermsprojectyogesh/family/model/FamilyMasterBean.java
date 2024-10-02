package com.yogesh.scalermsprojectyogesh.family.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yogesh.scalermsprojectyogesh.family.model.entity.FamilyMaster;
import com.yogesh.scalermsprojectyogesh.service.RequestMapper;
import com.yogesh.scalermsprojectyogesh.user.model.UserMasterBean;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilyMasterBean implements RequestMapper<FamilyMaster> {

    private long id;
    private String familyName;
    private List<UserMasterBean> familyMembers;
    private BigDecimal familyFund;
    private BigDecimal availableFamilyFund;
    private BigDecimal addAdditionalFund;

    @Override
    public FamilyMaster createEntityBean() {
        return FamilyMaster.builder()
                .familyName(familyName)
                .users(familyMembers.stream().map(UserMasterBean::createEntityBean).collect(Collectors.toList()))
                .familyFund(familyFund)
                .build();
    }
}
