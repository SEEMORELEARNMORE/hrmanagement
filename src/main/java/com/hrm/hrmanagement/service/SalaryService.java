package com.hrm.hrmanagement.service;

import com.hrm.hrmanagement.entity.Salary;
import com.hrm.hrmanagement.mapper.PersonMapper;
import com.hrm.hrmanagement.mapper.SalaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

@Service
public class SalaryService {
    @Autowired private SalaryMapper salaryMapper;
    @Autowired private PersonMapper personMapper;
    public Integer update(@RequestBody Salary salary){
        if(personMapper.findById(salary.getId())==null)
            return -1;
        salary.setDepartment(personMapper.findDep(Integer.valueOf(salary.getId().substring(0,3))));
        salary.setName(personMapper.findName(salary.getId()));
        salary.setTotal(salary.getBasic_wage()+salary.getFive_one()+salary.getPerformance_pay()+salary.getSubsidies()-salary.getOther_deductions());
        return salaryMapper.update(salary);
    }
    public Integer add(@RequestBody Salary salary){
//        System.out.println("id不存在的情况："+personMapper.findById(salary.getId()));
        if(personMapper.findById(salary.getId())==null)
            return -1;
        salary.setDepartment(personMapper.findDep(Integer.valueOf(salary.getId().substring(0,3))));
        salary.setName(personMapper.findName(salary.getId()));
        salary.setTotal(salary.getBasic_wage()+salary.getFive_one()+salary.getPerformance_pay()+salary.getSubsidies()-salary.getOther_deductions());
        return salaryMapper.add(salary);
    }

}
