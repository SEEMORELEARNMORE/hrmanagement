package com.hrm.hrmanagement.mapper;

import com.hrm.hrmanagement.entity.Change;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ChangeMapper {
    @Insert("INSERT INTO personnel(person,changecode,description) VALUES(#{person},#{changecode},#{description});")
    Integer insert (Change change);

    List<Change> findAll();

    List<Change> findByCondition(String person,Integer changecode);
}
