package com.hrm.hrmanagement.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SalaryTransVo {
    String id;
    String name;
    String startTime;
    String endTime;
}
