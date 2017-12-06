package com.xiongkuang.controller;

import com.xiongkuang.common.utils.FastDFSClient;
import com.xiongkuang.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 图片上传Controller
 * Created by xiongkuang on 05/12/2017.
 */
@Controller
public class PictureController {

    @Value("${IMAGE_SERVER_URL}")
    private String imageServerUrl;

    @RequestMapping(value = "/pic/upload", produces = MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
    @ResponseBody
    public String uploadFile(MultipartFile uploadFile){
        try {
            //把图片上传到图片服务器
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
            String originalFileName = uploadFile.getOriginalFilename();
            String extName = originalFileName.substring(originalFileName.lastIndexOf(".")+1);
            String filePath = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            //得到完整图片路径
            String url = imageServerUrl + filePath;
            //封装返回
            Map res = new HashMap<>();
            res.put("error", 0);
            res.put("url", url);
            return JsonUtils.objectToJson(res);
        } catch (Exception e) {
            e.printStackTrace();
            Map res = new HashMap<>();
            res.put("error", 1);
            res.put("message", "Upload Failed");
            return JsonUtils.objectToJson(res);
        }


    }
}
