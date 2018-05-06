package com.bufoon.storage.fdfs.config;

import com.alibaba.fastjson.JSON;
import com.bufoon.commons.base.ProjectInfo;
import com.bufoon.storage.fdfs.FastdfsPool;
import com.bufoon.storage.fdfs.fastdfs.*;
import com.sun.deploy.ui.AppInfo;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/2/27 14:01
 * @Desc: as follows.
 */
@Configuration
@EnableConfigurationProperties({FastdfsProperties.class})
public class FastdfsConfig {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private FastdfsProperties fastdfsProperties;

    public FastdfsConfig(FastdfsProperties fastdfsProperties){
        this.fastdfsProperties = fastdfsProperties;
    }

    @Bean
    public FastdfsPool fastdfsPool(){
        return new FastdfsPool(fastdfsProperties);
    }

    @Bean
    public StorageClient1 storageClient(){
        if (fastdfsProperties == null){
            return null;
        }
        StorageClient1 storageClient = null;
        try
        {
            ClientGlobal.init(fastdfsProperties);
            System.out.println("ClientGlobal.configInfo(): " + ClientGlobal.configInfo());
            TrackerClient trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
            TrackerServer trackerServer = trackerClient.getConnection();
            if (trackerServer == null)
            {
                throw new IllegalStateException("getConnection return null");
            }
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            if (storageServer == null)
            {
                throw new IllegalStateException("getStoreStorage return null");
            }
            storageClient = new StorageClient1(trackerServer, storageServer);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return storageClient;
    }
}
