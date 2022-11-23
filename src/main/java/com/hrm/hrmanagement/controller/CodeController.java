package com.hrm.hrmanagement.controller;

import com.hrm.hrmanagement.entity.Code;
import com.hrm.hrmanagement.mapper.CodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CodeController {

    @Autowired
    private CodeMapper codeMapper;

    @GetMapping("/codeOptions")
    public Map index(){
        Map map = new HashMap();
        List<Code> job = codeMapper.findJobOptions();
        List<Code> dep = codeMapper.findDerOptions();
        List<Code> edu = codeMapper.findEduOptions();
        map.put("job",job);
        map.put("dep",dep);
        map.put("edu",edu);
        return map;
    }
}
