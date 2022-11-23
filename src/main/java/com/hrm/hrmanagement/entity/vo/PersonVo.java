package com.hrm.hrmanagement.entity.vo;

import lombok.Data;

@Data
public class PersonVo {
    private String id;
    private String passwd;
    private Integer authority;
    private String name;
    private String sex;
    private String birthday;
    private String department;
    private String job;
    private String edu_level;
    private String spcialty;
    private String address;
    private String tel;
    private String email;
    private String state;
    private String remark;
    private String avatar;
}
