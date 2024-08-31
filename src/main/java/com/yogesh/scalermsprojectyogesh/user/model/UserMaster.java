package com.yogesh.scalermsprojectyogesh.user.model;

import com.yogesh.scalermsprojectyogesh.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserMaster extends BaseModel {

    private String name;
    private String userName;
    private String email;
    private String password;
    private String phone;
    private int corporateId;
    private Date joinDate;
    private List<String> roles;
 }

