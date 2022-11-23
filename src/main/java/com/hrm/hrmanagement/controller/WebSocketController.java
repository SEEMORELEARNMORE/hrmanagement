package com.hrm.hrmanagement.controller;

import com.hrm.hrmanagement.service.WebSocketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/websocket")
public class WebSocketController {


    @GetMapping("/pushone")
    public void pushone()
    {
        System.out.println("正在建立连接！");
    }
}
