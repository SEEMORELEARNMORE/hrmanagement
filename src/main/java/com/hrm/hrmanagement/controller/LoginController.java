package com.hrm.hrmanagement.controller;

import com.hrm.hrmanagement.entity.Person;
import com.hrm.hrmanagement.entity.vo.Login;
import com.hrm.hrmanagement.mapper.LoginMapper;
import com.hrm.hrmanagement.mapper.PersonMapper;
import com.hrm.hrmanagement.service.LoginService;
import com.hrm.hrmanagement.entity.vo.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//统一前缀
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/match")
    public Person find(@RequestBody Login login) {
        Person P = loginService.matchInfo(login.getId(),login.getPasswd());
//        System.out.println("后端返回的前端验证数据："+P);
//        Token token=new Token();
//        token.setToken("admin-token");
        return P;
    }
}
