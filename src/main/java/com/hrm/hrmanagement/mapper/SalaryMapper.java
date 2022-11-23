package com.hrm.hrmanagement.mapper;

import com.hrm.hrmanagement.entity.Salary;
import com.hrm.hrmanagement.entity.vo.SalaryTransVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

@Mapper
public interface SalaryMapper {
    List<Salary> findAll();
    @Delete("delete from salary where id=#{id} and month=#{month}")
    Integer delete(String id, Date month);

    @Insert("insert into salary(id,name,department,month ,basic_wage,performance_pay,five_one,subsidies,other_deductions,total,remark) values(#{id},#{name},#{department},#{month} ,#{basic_wage},#{performance_pay},#{five_one},#{subsidies},#{other_deductions},#{total},#{remark})")
    Integer add(Salary salary);

    @Update("update salary set name=#{name},department=#{department},month=#{month},basic_wage=#{basic_wage},performance_pay=#{performance_pay},five_one=#{five_one},subsidies=#{subsidies},other_deductions=#{other_deductions},total=#{total},remark=#{remark} where id=#{id}")
    Integer update(Salary salary);

    List<Salary> findbycondition(SalaryTransVo salaryTransVoVo);
}
