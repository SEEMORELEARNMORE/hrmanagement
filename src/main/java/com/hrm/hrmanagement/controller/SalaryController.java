package com.hrm.hrmanagement.controller;

import com.hrm.hrmanagement.entity.Salary;
import com.hrm.hrmanagement.entity.vo.SalaryTransVo;
import com.hrm.hrmanagement.mapper.SalaryMapper;
import com.hrm.hrmanagement.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
//统一前缀
@RequestMapping("/salary")
public class SalaryController {

    @Autowired
    private SalaryMapper salaryMapper;
    @Autowired
    private SalaryService salaryService;

    @PostMapping("/add")
    public Integer insert(@RequestBody Salary salary){
        return salaryService.add(salary);
    }

    @PostMapping("/update")
    public Integer update(@RequestBody Salary salary){
        return salaryService.update(salary);
    }

    @GetMapping("/findall")
    public List<Salary> index(){
        List<Salary> S =salaryMapper.findAll();
        return S;
    }

//    根据条件查找数据
    @PostMapping("/findbycondition")
    public List<Salary> findData(@RequestBody SalaryTransVo salaryTransVo){
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        SalarySearchVo S=new SalarySearchVo();
//        S.setId(salaryTransVo.getId());
//        S.setName(salaryTransVo.getName());
//        if(salaryTransVo.getStartTime()!=null){
//            try {
//                java.util.Date dd = format.parse(salaryTransVo.getStartTime());
////                System.out.println(salaryTransVo.getStartTime().equals(format.format(dd)));
//                S.setStartTime(format.parse(salaryTransVo.getStartTime()));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//if(salaryTransVo.getEndTime()!=null){
//    try {
//        S.setEndTime(format.parse(salaryTransVo.getEndTime()));
//    } catch (ParseException e) {
//        e.printStackTrace();
//    }
//}
        return  salaryMapper.findbycondition(salaryTransVo);
    }

    @DeleteMapping("/delete")
    public Integer delete(@RequestParam(value = "id", required = true) String id, @RequestParam(value = "month", required = true) Date month){
        return salaryMapper.delete(id,month);
    }

}
