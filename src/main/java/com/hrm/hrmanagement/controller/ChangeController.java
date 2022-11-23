package com.hrm.hrmanagement.controller;

import com.hrm.hrmanagement.entity.Change;
import com.hrm.hrmanagement.entity.vo.Id;
import com.hrm.hrmanagement.mapper.ChangeMapper;
import com.hrm.hrmanagement.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//统一前缀
@RequestMapping("/change")
public class ChangeController {
    @Autowired(required = false)
    private ChangeMapper changeMapper;
    @GetMapping("/findall")
    public List<Change> find(){
        List<Change> P= changeMapper.findAll();
        return P;
    }

    @GetMapping("/findbycondition")
    public List<Change> find(@RequestParam(value = "person", required = false) String person, @RequestParam(value = "changecode", required = false) Integer changecode){
        List<Change> P= changeMapper.findByCondition(person,changecode);
        return P;
    }

}
