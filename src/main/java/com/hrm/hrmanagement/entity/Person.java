package com.hrm.hrmanagement.entity;

import lombok.Data;

@Data
public class Person {
    private String id;
    private String passwd;
    private Integer authority;
    private String name;
    private String sex;
    private String birthday;
    private Integer department;
    private Integer job;
    private Integer edu_level;
    private String spcialty;
    private String address;
    private String tel;
    private String email;
    private String state;
    private String remark;
    private String avatar;
}
