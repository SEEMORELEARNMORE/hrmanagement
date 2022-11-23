package com.hrm.hrmanagement.mapper;

import com.hrm.hrmanagement.entity.Person;
import com.hrm.hrmanagement.entity.vo.AvatarVo;
import com.hrm.hrmanagement.entity.vo.Id;
import com.hrm.hrmanagement.entity.vo.PersonVo;
import com.hrm.hrmanagement.entity.vo.PswVo;
import org.apache.ibatis.annotations.*;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;

//用于数据库交互
//注解实现bean注入springboot
@Mapper
public interface PersonMapper {

//    查找所有的记录
    List<Person> findAll();
//    @Select("SELECT * FROM person where state="F"")
//    根据员工状态查找
    List<Person> findByState(@Param("state") String state);
//    根据员工id查找
    PersonVo findById(@Param("id") String id);
    //    根距code查找部门
    @Select("select description from department where code=#{code}")
    String findDep(@Param("code")Integer code);
//  根据员工id找姓名
    @Select("select name from person where id=#{id}")
    String findName(@Param("id") String id);
// #{}实现动态
    @Insert("INSERT INTO person(id,passwd,authority,name,sex,birthday,department,job,edu_level,spcialty,address,tel,email,state,remark) VALUES(#{id},#{passwd},#{authority},#{name},#{sex},#{birthday},#{department},#{job},#{edu_level},#{spcialty},#{address},#{tel},#{email},#{state},#{remark});")
    Integer insert(Person person);

//    注解或xml中写sql语句二选一
    Integer update(Person person);


    Integer deleteById(@Param("id") String id);

    Integer toPositive(String id);
    Integer findNewId(Integer department,Integer job);
    List<Id> findAllId();
    List<Person> findByCondition(String id, String name,String state,Integer department,Integer job);

    //    param与sql语句中的#{}内容一一对应
    Integer updatePsw(PswVo pswVo) ;

    Integer updateAuth(String id);

    List<HashMap<String, Object>> divideByEdu();

    List<HashMap<String, Object>> divideDep();

    @Update("update person set avatar=#{avatar} where id=#{id}")
    Integer uploadavatar(AvatarVo avatarVo);
}
