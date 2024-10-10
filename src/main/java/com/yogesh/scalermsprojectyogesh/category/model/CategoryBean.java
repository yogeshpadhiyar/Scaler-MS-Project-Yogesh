package com.yogesh.scalermsprojectyogesh.category.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yogesh.scalermsprojectyogesh.category.model.entity.Category;
import com.yogesh.scalermsprojectyogesh.service.RequestMapper;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryBean implements RequestMapper<Category> {

    private Long categoryId;
    private Long parentCategoryId;
    private Long familyId;
    private Long userId;
    private String name;
    private String description;
    private BigDecimal totalFund;
    private BigDecimal availableFund;
    private boolean active = true;


    @Override
    public Category createEntityBean() {
        return Category.builder()
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
