package com.yogesh.scalermsprojectyogesh.user.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yogesh.scalermsprojectyogesh.service.RequestMapper;
import com.yogesh.scalermsprojectyogesh.user.model.entity.Role;
import com.yogesh.scalermsprojectyogesh.user.model.entity.UserMaster;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserMasterBean implements RequestMapper<UserMaster>{

    private String name;
    private String username;
    private String emailId;
    private String password;
    private String phone;
    private Set<String> roles;
    private Boolean isParentOfFamily;
    private String familyName;

    @Override
    public UserMaster createEntityBean() {
        return UserMaster.builder()
                .name(name)
                .username(username)
                .emailId(emailId)
                .phone(phone)
                .password(password)
                .isParentOfFamily(isParentOfFamily)
                .build();
    }
}
