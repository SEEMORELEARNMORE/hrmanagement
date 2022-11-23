package com.hrm.hrmanagement.mapper;

import com.hrm.hrmanagement.entity.Article;
import com.hrm.hrmanagement.entity.vo.ArticleVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ArticleMapper {
    @Insert("insert into notice(author,author_id,title,body,time) values(#{author},#{author_id},#{title},#{body},#{time});")
    Integer insert(Article article);

    @Select("select id from notice where author=#{author} and  title=#{title} and body=#{body} and time=#{time}")
    Integer findId(Article article);

    @Select("select * from notice order by id desc")
    List<Article> getlist();

    List<Article> findbycondition(ArticleVo articleVo);

    @Select("select * from notice where id=#{id}")
    Article getbyid(Integer id);

    @Select("select notice_id from temp_notice where user_id=#{id} order by notice_id desc")
    List<Integer> getlistwithstate(String id);

}
