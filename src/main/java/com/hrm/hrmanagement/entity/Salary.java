package com.hrm.hrmanagement.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Salary {
    String id;
    String name;
    String department;
    String month;
    Integer basic_wage;
    Integer performance_pay;
    Integer five_one;
    Integer subsidies;
    Integer other_deductions;
    Integer  total;
    String remark;
}
