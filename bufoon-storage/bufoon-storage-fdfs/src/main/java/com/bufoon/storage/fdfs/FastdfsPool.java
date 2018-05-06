package com.bufoon.storage.fdfs;

import com.bufoon.storage.fdfs.config.FastdfsProperties;
import com.bufoon.storage.fdfs.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/2/27 15:50
 * @Desc: as follows.
 */

public class FastdfsPool {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /** 空闲的连接池 */
    private LinkedBlockingQueue<StorageClient1> idleConnectionPool = null;

    private TrackerServer trackerServer = null;

    private FastdfsProperties properties = null;

    /**
     * 默认构造方法
     */
    public FastdfsPool(FastdfsProperties properties)
    {
        this.properties = properties;
        /** 初始化连接池 */
        poolInit();
    }

    /**
     * @Description: 连接池初始化 (在加载当前ConnectionPool时执行) 1).加载配置文件 2).空闲连接池初始化；
     *               3).创建最小连接数的连接，并放入到空闲连接池；
     */
    private void poolInit()
    {
        try
        {
            /** 加载配置文件 */
            initClientGlobal();
            /** 初始化空闲连接池 */
            idleConnectionPool = new LinkedBlockingQueue<>();
            /** 初始化忙碌连接池 */
            // busyConnectionPool = new ConcurrentHashMap<StorageClient1, Object>();
            TrackerGroup trackerGroup = ClientGlobal.g_tracker_group;
            TrackerClient trackerClient = new TrackerClient(trackerGroup);
            trackerServer = trackerClient.getConnection();
            int flag = 0;
            while (trackerServer == null && flag < properties.getRetryConnectTimes())
            {
                System.out.println("[创建TrackerServer(createTrackerServer)][第" + flag + "次重建]");
                flag++ ;
                initClientGlobal();
                trackerServer = trackerClient.getConnection();
            }
            // 测试 Tracker活跃情况
            // ProtoCommon.activeTest(trackerServer.getSocket());

            /** 往线程池中添加默认大小的线程 */
            createTrackerServer();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("[FASTDFS初始化(init)--异常]");
        }
    }

    /**
     * @Description: 创建TrackerServer,并放入空闲连接池
     */
    public void createTrackerServer()
    {

        logger.info("[创建TrackerServer(createTrackerServer)]");
        TrackerServer trackerServer = null;

        try
        {

            for (int i = 0; i < properties.getMinPoolSize(); i++ )
            {
                // 把client1添加到连接池
                StorageServer storageServer = null;
                StorageClient1 client1 = new StorageClient1(trackerServer, storageServer);
                idleConnectionPool.add(client1);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("[创建TrackerServer(createTrackerServer)][异常：{}]", e.getMessage());
        }

    }

    /**
     * @Description: 获取空闲连接 1).在空闲池（idleConnectionPool)中弹出一个连接； 2).把该连接放入忙碌池（busyConnectionPool）中;
     *               3).返回 connection 4).如果没有idle connection, 等待 wait_time秒, and check again
     *
     */
    public StorageClient1 acquire()
    {

        StorageClient1 client1 = idleConnectionPool.poll();

        if (client1 == null)
        {
            if (idleConnectionPool.size() < properties.getMaxPoolSize())
            {
                createTrackerServer();
                try
                {
                    client1 = idleConnectionPool.poll(properties.getWaitTimes(), TimeUnit.SECONDS);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    logger.info("[获取空闲连接(checkout)-error][error:获取连接超时:{}]");
                }
            }
        }

        // 添加到忙碌连接池
        // busyConnectionPool.put(client1, obj);
       logger.info("[获取空闲连接(acquire)][获取空闲连接成功]");
        return client1;
    }

    /**
     * @Description: 释放繁忙连接 1.如果空闲池的连接小于最小连接值，就把当前连接放入idleConnectionPool；
     *               2.如果空闲池的连接等于或大于最小连接值，就把当前释放连接丢弃；
     * @param client1
     *            需释放的连接对象
     */

    public void release(StorageClient1 client1)
    {

        logger.info("[释放当前连接]");
        if (client1 != null){
            client1 = null;
        }
        if (idleConnectionPool.size() < properties.getMinPoolSize())
        {
            createTrackerServer();
        }

    }

    private void initClientGlobal()
            throws Exception
    {
        ClientGlobal.init(properties);
    }

    public LinkedBlockingQueue<StorageClient1> getIdleConnectionPool()
    {
        return idleConnectionPool;
    }

    public long getMinPoolSize()
    {
        return properties.getMinPoolSize();
    }


    public long getMaxPoolSize()
    {
        return properties.getMaxPoolSize();
    }

    public long getWaitTimes()
    {
        return properties.getWaitTimes();
    }

}
