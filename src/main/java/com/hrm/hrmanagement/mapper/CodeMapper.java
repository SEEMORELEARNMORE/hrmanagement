package com.hrm.hrmanagement.mapper;

import com.hrm.hrmanagement.entity.Code;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CodeMapper {
    String findJob(@Param("code") Integer code);
    String findDep(@Param("code") Integer code);
    List<Code> findJobOptions();
    List<Code> findDerOptions();
    List<Code> findEduOptions();

}
