package com.hrm.hrmanagement;

import com.hrm.hrmanagement.entity.Person;
import com.hrm.hrmanagement.utils.MD5Utils;
import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.net.InetAddress;

@Slf4j
@SpringBootApplication
public class HrmanagementApplication {

    public static void main(String[] args)  throws Exception {
//        SpringApplication.run(HrmanagementApplication.class, args);
//        springboot自带MD5加密
        Person person;
        System.out.println("加密结果："+MD5Utils.inputPassToFormPass("000000"));
        ConfigurableApplicationContext application =  SpringApplication.run(HrmanagementApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String property = env.getProperty("server.servlet.context-path");
        String path = property == null ? "" :  property;
        System.out.println(
                "\n\t" +
                        "----------------------------------------------------------\n\t" +
                        "Application Sailrui-Boot is running! Access URLs:\n\t" +
                        "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                        "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                        "------------------------------------------------------------");

    }

}
