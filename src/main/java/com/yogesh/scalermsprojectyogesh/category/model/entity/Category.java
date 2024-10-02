package com.yogesh.scalermsprojectyogesh.category.model.entity;

import com.yogesh.scalermsprojectyogesh.category.model.CategoryBean;
import com.yogesh.scalermsprojectyogesh.model.BaseModel;
import com.yogesh.scalermsprojectyogesh.service.ResponseMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class Category extends BaseModel implements ResponseMapper<CategoryBean> {

    private Long parentCategoryId;
    @Column(nullable = false)
    private Long familyId;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String name;
    private String description;
    private BigDecimal totalFund;
    private BigDecimal availableFund;
    private Boolean active = true;

    @Override
    public CategoryBean createResponseBean() {
        return CategoryBean.builder()
                .parentCategoryId(parentCategoryId)
                .familyId(familyId)
                .userId(userId)
                .name(name)
                .description(description)
                .totalFund(totalFund)
                .availableFund(availableFund)
                .active(active)
                .build();
    }
}
