package com.hrm.hrmanagement.mapper;

import com.hrm.hrmanagement.entity.vo.Id;
import com.hrm.hrmanagement.entity.vo.ReadVo;
import com.hrm.hrmanagement.entity.vo.TempNoticeVo;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Mapper
@Component
public interface TempNoticeMapper {

    Integer addList(@Param("id") Integer id, @Param("ids")List<Id> ids);

    @Select("select notice_id,state from temp_notice where user_id=#{user_id}")
    List<TempNoticeVo> selectList (String user_id);

    @Select("select count(notice_id) from temp_notice where user_id=#{user_id}")
    Integer total (String user_id);

    @Select("select * from temp_notice where user_id=#{id} and notice_id =#{notice_id}")
    List<TempNoticeVo> find (@RequestBody ReadVo readVo);

    @Select("select notice_id from temp_notice where user_id=#{id}")
    List<Integer> isUnread(String id);

    @Delete("delete from temp_notice where user_id=#{id} and notice_id=#{notice_id}")
    Integer delete(@RequestBody ReadVo readVo);
}
