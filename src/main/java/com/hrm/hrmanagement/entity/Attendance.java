package com.hrm.hrmanagement.entity;

import lombok.Data;

@Data
public class Attendance {
    String user_id;
    String time;
    Boolean finished;
    Integer record_id;
    String name;
}
