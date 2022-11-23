package com.hrm.hrmanagement.mapper;

import com.hrm.hrmanagement.entity.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LoginMapper {

    @Select("select * from person where id=#{id} and passwd=#{passwd}")
    Person matchInfo(String id, String passwd);
}
