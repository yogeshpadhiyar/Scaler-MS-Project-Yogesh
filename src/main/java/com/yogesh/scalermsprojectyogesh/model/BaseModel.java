package com.yogesh.scalermsprojectyogesh.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseModel {
    public static final String DATE_FORMAT_UTC_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(nullable = false,updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT_UTC_TIMEZONE, timezone = "IST")
    private Date created;

    @LastModifiedDate
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT_UTC_TIMEZONE, timezone = "IST")
    private Date updated;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;
}
