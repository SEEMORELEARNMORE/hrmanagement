package com.hrm.hrmanagement.controller;

import com.hrm.hrmanagement.entity.Attendance;
import com.hrm.hrmanagement.entity.vo.AttendanceVo;
import com.hrm.hrmanagement.mapper.AttendanceMapper;
import com.hrm.hrmanagement.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//统一前缀
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired private AttendanceMapper attendanceMapper;

    @Autowired private PersonMapper personMapper;

    @GetMapping("/findall")
    public List<Attendance> index(){
        return attendanceMapper.findAll();
    }

    @PostMapping("/add")
    public Integer add(@RequestBody Attendance attendance){
        if(personMapper.findById(attendance.getUser_id())==null)
            return -1;
        return attendanceMapper.add(attendance);
    }

    @GetMapping("/update")
    public Integer update(@RequestParam(value = "record_id", required = true) Integer record_id,@RequestParam(value = "finished", required = true) Boolean finished){
        return attendanceMapper.update(record_id,finished);
    }

    @PostMapping("/findbycondition")
    public List<Attendance>  findbycondition(@RequestBody AttendanceVo attendanceVo){
        System.out.println(attendanceVo);
        return attendanceMapper.findbycondition(attendanceVo);
    }
}
