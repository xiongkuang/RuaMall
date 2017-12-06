package com.xiongkuang.fast;

import com.xiongkuang.common.utils.FastDFSClient;
import org.csource.fastdfs.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xiongkuang on 05/12/2017.
 */
public class FastDfsTest {

    @Test
    public void testUpload() throws Exception{
        //创建一个配置文件，内容为tracker服务器地址
        //使用全剧对象来加载配置文件
        ClientGlobal.init("/Users/xiongkuang/Desktop/practice/RuaMall/rua-manager-web/src/main/resources/conf/client.conf");

        //创建TrackerClient
        TrackerClient trackerClient = new TrackerClient();

        //通过TrackerClient获得TrackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();

        //创建一个storageServer的引用，可以是null
        StorageServer storageServer = null;

        //创建一个storageClient
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);

        //使用StorageClients上传文件
        String[] strs = storageClient.upload_file("/Users/xiongkuang/Downloads/dd.jpg", "jpg", null);

        for(String s : strs){
            System.out.println(s);
        }
    }


    @Test
    public void fastDfsClientTest() throws Exception{
        FastDFSClient fastDFSClient = new FastDFSClient("/Users/xiongkuang/Desktop/practice/RuaMall/rua-manager-web/src/main/resources/conf/client.conf");
        String str =  fastDFSClient.uploadFile("/Users/xiongkuang/Downloads/dendi.jpg");
        System.out.println(str);
    }

}