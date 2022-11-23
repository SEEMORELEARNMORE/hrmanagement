package com.hrm.hrmanagement.service;

import com.hrm.hrmanagement.entity.Change;
import com.hrm.hrmanagement.entity.Person;
import com.hrm.hrmanagement.entity.vo.PersonVo;
import com.hrm.hrmanagement.entity.vo.PswVo;
import com.hrm.hrmanagement.mapper.ChangeMapper;
import com.hrm.hrmanagement.mapper.CodeMapper;
import com.hrm.hrmanagement.mapper.PersonMapper;
import com.hrm.hrmanagement.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//将service注入springboot
@Service
public class PersonService {
    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private ChangeMapper changeMapper;

    @Autowired
    private CodeMapper codeMapper;


    public Integer insert(Person person){
        person.setState("P");
        Integer num = personMapper.findNewId(person.getDepartment(),person.getJob());
        String id = person.getDepartment() + padZero(person.getJob())+padZero((num+1));
        person.setId(id);
        person.setAuthority(0);
        person.setPasswd(MD5Utils.inputPassToFormPass(person.getId()));
        changeMapper.insert(new Change(person.getId(),0,"新增员工"));
            return personMapper.insert(person);
    }
    public String padZero(Integer str) {
        return str > 9 ? str.toString() : '0' + str.toString();
    }
    public Integer deleteById(String id){
        changeMapper.insert(new Change(id,2,"员工离职"));
        return personMapper.deleteById((id));
    }

    public  Integer update(Person person){

        PersonVo p1=personMapper.findById(person.getId());
        if(p1.getDepartment()!=codeMapper.findDep(person.getDepartment())||p1.getJob()!=codeMapper.findJob(person.getJob())){
            String oldJob=p1.getJob();
            String newJob=codeMapper.findJob(person.getJob());
            String oldDep=p1.getDepartment();
            String newDep=codeMapper.findDep(person.getDepartment());
            changeMapper.insert(new Change(person.getId(),1,"人事调动："+oldDep+"部"+oldJob+"->"+newDep+"部"+newJob));
        }
        return personMapper.update(person);
    }

    public Integer updatePsw(PswVo pswVo) {
        pswVo.setPasswd(MD5Utils.inputPassToFormPass(pswVo.getPasswd()));
        return personMapper.updatePsw(pswVo);
    }

}
