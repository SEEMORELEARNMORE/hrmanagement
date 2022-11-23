package com.hrm.hrmanagement.service;

import com.hrm.hrmanagement.entity.Person;
import com.hrm.hrmanagement.mapper.CodeMapper;
import com.hrm.hrmanagement.mapper.LoginMapper;
import com.hrm.hrmanagement.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LoginService {
    @Autowired
    private LoginMapper loginMapper;

    public Person matchInfo(String id, String passwd){
        passwd=(MD5Utils.inputPassToFormPass(passwd));
        return loginMapper.matchInfo(id,passwd);
    }
}
