package com.hrm.hrmanagement.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.InetAddress;
import java.util.UUID;

@Service
public class UploadImgService {
    /**
     * 图片保存路径，自动从yml配置文件中获取数据
     */
    @Value("${file.uploadFolder}")
    private String uploadPath;


    /**
     * 文件（图片）上传
     * @param file 图片文件
     */
    @SneakyThrows

    public String uploadFile(MultipartFile file,HttpServletRequest request) {
        System.out.println("前端传输过来的数据："+file);
//        return "haha";
        //文件原名称
        String oldFilename = file.getOriginalFilename();
        //图片名后缀：.jpg、.png
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //uuid
        String uuid = UUID.randomUUID().toString();
        //文件新名称
        String newFileName = uuid + suffix;

        //创建保存上传文件的文件夹
        File folder = new File(uploadPath + newFileName);
        if (!folder.getParentFile().exists()) {
            folder.getParentFile().mkdirs();
        }
        //文件写入到该文件夹下
        file.transferTo(folder);

        //获得本机Ip（获取的是服务器的Ip）
        InetAddress inetAddress = InetAddress.getLocalHost();
        String ip = inetAddress.getHostAddress();
        //返回保存的url，根据url可以进行文件查看或者下载
        String fileDownloadUrl = request.getScheme() + "://" + ip + ":" + request.getServerPort() + "/api/file/" + newFileName;

        //ps: 在这里可以把路径url存到数据库
        //Entity entity = new Entity()
        //entity.setUrl(fileDownloadUrl);
        //返回保存的url
        return fileDownloadUrl;
    }
}

