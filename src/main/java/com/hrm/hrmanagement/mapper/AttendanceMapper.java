package com.hrm.hrmanagement.mapper;

import com.hrm.hrmanagement.entity.Attendance;
import com.hrm.hrmanagement.entity.vo.AttendanceVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AttendanceMapper {
    @Select("select * from attendance")
    List<Attendance> findAll();

    @Insert("insert into attendance(user_id,time,finished,name) values(#{user_id},#{time},#{finished},#{name})")
    Integer add(Attendance attendance);

    Integer update(Integer record_id,Boolean finished);

    List<Attendance> findbycondition(AttendanceVo attendanceVo);
}
