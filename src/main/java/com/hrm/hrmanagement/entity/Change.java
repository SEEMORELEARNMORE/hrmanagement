package com.hrm.hrmanagement.entity;

import lombok.Data;

@Data
public class Change {
    Integer id;
    Integer changecode;
    String person;
    String description;

    public Change(String p, int i, String d) {
        this.person=p;
        this.changecode=i;
        this.description=d;
    }
}
