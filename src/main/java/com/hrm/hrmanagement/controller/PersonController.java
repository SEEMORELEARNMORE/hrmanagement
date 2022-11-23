package com.hrm.hrmanagement.controller;

import com.hrm.hrmanagement.entity.Person;
import com.hrm.hrmanagement.entity.vo.AvatarVo;
import com.hrm.hrmanagement.entity.vo.Id;
import com.hrm.hrmanagement.entity.vo.PersonVo;
import com.hrm.hrmanagement.entity.vo.PswVo;
import com.hrm.hrmanagement.mapper.PersonMapper;
import com.hrm.hrmanagement.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
@RestController
//统一前缀
@RequestMapping("/person")
public class PersonController {
    //    注入其他类
    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private PersonService personService;

    //    新增
    @PostMapping("/add")
    public Integer insert(@RequestBody Person person) {
        return personService.insert(person);
    }

    //    更新
    @PostMapping("/update")
    public Integer update(@RequestBody Person person) {
        return personService.update(person);
    }

//    修改密码
//@PostMapping("/updatepsw")
    @RequestMapping(value = "/updatepsw",method = RequestMethod.POST)
public Integer updatePsw(@RequestBody PswVo pswVo) {
    return personService.updatePsw(pswVo);
}

//修改权限
@RequestMapping(value = "/updateauth",method = RequestMethod.POST)
public Integer updatePsw(String id) {
    return personMapper.updateAuth(id);
}
    //    requestbody 把前端传过来的json对象映射成person对象
//    public Integer save(@RequestBody Person person){
////        新增或者更新
//        return personService.sava(person);
//    }
    // getmapping 依赖于restcontroller

//    找所有记录
    @GetMapping("/findall")
    public List<Person> index(){
        List<Person> P = personMapper.findAll();
        return P;
    }
//    根据状态查询
    @GetMapping("/findbystate/{state}")
    public List<Person> index(@PathVariable String state) {
        List<Person> P = personMapper.findByState(state);
        return P;
    }
    @GetMapping("/findbyid/{id}")
    public PersonVo indexId(@PathVariable String id) {
        PersonVo P = personMapper.findById(id);
        return P;
    }

    //    根据学历分组
    @GetMapping("/dividebyedu")
    public List<HashMap<String,Object>> divide() {
        List<HashMap<String, Object>> P = personMapper.divideByEdu();
        return P;
    }

    //    根据部门分组
    @GetMapping("/dividebydep")
    public List<HashMap<String,Object>> divideDep() {
        List<HashMap<String, Object>> P = personMapper.divideDep();
        return P;
    }
    // 查询所有id
    @GetMapping("/findallid")
    public List<Id> find() {
        List<Id> P = personMapper.findAllId();
        return P;
    }

    @DeleteMapping("/delete/{id}")
//    pathvariable中的参数对应mapping地址上的参数
    public Integer delete(@PathVariable String id) {
        return personService.deleteById(id);
    }

    @GetMapping("/topositive/{id}")
    public Integer toPositive(@PathVariable String id) {
        return personMapper.toPositive(id);
    }

    //    根据名字和员工id进行查询
    @GetMapping("/findbycondition")
    public List<Person> findtwo(@RequestParam(value = "id", required = false) String id, @RequestParam(value = "name", required = false) String name,@RequestParam(value = "state", required = false) String state,@RequestParam(value = "department", required = false) Integer department,@RequestParam(value = "job", required = false) Integer job) {
        List<Person> P = personMapper.findByCondition(id, name,state,department,job);
        return P;
    }
//    修改头像
//    @PostMapping("/uploadavatar")
    @RequestMapping(value ="/uploadavatar",method =RequestMethod.POST )    public Integer uploadavatar(@RequestBody AvatarVo avatarVo) {
        return personMapper.uploadavatar(avatarVo);
    }
}

