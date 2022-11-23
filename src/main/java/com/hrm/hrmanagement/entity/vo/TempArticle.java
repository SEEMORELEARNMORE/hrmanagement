package com.hrm.hrmanagement.entity.vo;

import lombok.Data;
@Data
public class TempArticle {


        Integer id;
        String author;
        String author_id;
        String title;
        String body;
        String time;
        Boolean state;

        public TempArticle(
                Integer id,
                String author,
                String author_id,
                String title,
                String body,
                String time,
                Boolean state){
            this.id=id;
            this.author=author;
            this.author_id = author_id;
            this.title=title;
            this.body=body;
            this.time=time;
            this.state=state;

        }

}
