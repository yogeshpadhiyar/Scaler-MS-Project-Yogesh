package com.yogesh.scalermsprojectyogesh.user.model;

import com.yogesh.scalermsprojectyogesh.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity()
@Table(name = "user_master")
public class UserMaster extends BaseModel {

    private String name;
    private String username;
    private String email;
    private String password;
    private String phone;
    private List<String> roles;
 }

