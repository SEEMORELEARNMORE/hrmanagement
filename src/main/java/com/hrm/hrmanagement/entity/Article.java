package com.hrm.hrmanagement.entity;

import lombok.Data;

@Data
public class Article {
    Integer id;
    String author;
    String author_id;
    String title;
    String body;
    String time;
}