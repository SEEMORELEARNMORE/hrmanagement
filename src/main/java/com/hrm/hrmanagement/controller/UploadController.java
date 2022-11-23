package com.hrm.hrmanagement.controller;

import com.hrm.hrmanagement.service.UploadImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private UploadImgService uploadImgService;

    @PostMapping("/uploadImg")
    public String uploadImg(@RequestBody MultipartFile file, HttpServletRequest request){
        String imgURL=uploadImgService.uploadFile(file,request);
        return imgURL;
    }
}

