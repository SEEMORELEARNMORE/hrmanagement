package com.hrm.hrmanagement.controller;

import com.hrm.hrmanagement.entity.vo.ReadVo;
import com.hrm.hrmanagement.entity.vo.TempNoticeVo;
import com.hrm.hrmanagement.mapper.TempNoticeMapper;
import com.hrm.hrmanagement.service.TempNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//统一前缀
@RequestMapping("/unread")
public class TempNoticeController {

    @Autowired
    private TempNoticeMapper tempNoticeMapper;

    @Autowired
    private TempNoticeService tempNoticeService;

    @GetMapping("/isunread/{id}")
    public  Boolean isUnread(@PathVariable String id){
        return tempNoticeService.isUnread(id);
    }

    @GetMapping("/total/{id}")
    public  Integer total(@PathVariable String id){
        return tempNoticeMapper.total(id);
    }

    @RequestMapping(value ="/read",method =RequestMethod.POST )
    public  Integer read(@RequestBody ReadVo readVo){
        List<TempNoticeVo> t=tempNoticeMapper.find(readVo);
        if(t.size()!=0){
           return tempNoticeMapper.delete(readVo);
        }
       return 1;
    }

}
