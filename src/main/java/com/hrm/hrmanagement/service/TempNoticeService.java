package com.hrm.hrmanagement.service;

import com.hrm.hrmanagement.mapper.TempNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TempNoticeService {
    @Autowired
    private TempNoticeMapper tempNoticeMapper;
    public Boolean isUnread (String id){
        List<Integer> s= tempNoticeMapper.isUnread(id);
        if(s.size()==0) return false;
        return true;
    }
}
