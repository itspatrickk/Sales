package com.patrick.restapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "MST_ROLE")
public class MstRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ROLE_ID")
    private Integer id;

    @Column(name = "ROLE_ACCESS")
    private String roleAccess;

    @Column(name = "START_DATE")
    private Date date;
}
