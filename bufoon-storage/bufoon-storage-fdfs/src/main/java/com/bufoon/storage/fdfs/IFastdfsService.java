package com.bufoon.storage.fdfs;

import com.bufoon.storage.fdfs.common.NameValuePair;
import com.bufoon.storage.fdfs.fastdfs.FileInfo;

import java.util.Map;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/2/27 10:19
 * @Desc: as follows.
 */
public interface IFastdfsService {

    /**
     * 初始化
     */
    //int init();

    /**
     * @param fileBuff 文件内容
     * @param fileExtName 文件后缀名
     * @param metaList 其他属性
     * @return 文件ID
     */
    String uploadFile(byte[] fileBuff, String fileExtName,
                      NameValuePair[] metaList);

    /**
     * @param fileBuff
     * @param fileExtName
     * @return 文件ID
     */
    String uploadFile(byte[] fileBuff, String fileExtName);


    String uploadFile(String filePath, String fileExtName);

    String uploadFile(String filePath, String fileExtName, NameValuePair[] metaList);

    /** 上传从文件
     * @param masterFileId
     * @param prefix
     * @param path
     * @param fileExtName
     * @param metaList
     * @return
     */
    String uploadFile(String masterFileId, String prefix, String path, String fileExtName, NameValuePair[] metaList);
    /**
     *
     * @version 1.0
     * @date 2016年12月6日 下午1:41:36
     * @param fileBuff  附件
     * @param masterFileId  附件主路径
     * @param prefix        附件名称增加信息
     * @param fileExtName   附件后缀
     * @return
     */
    String uploadFile(byte[] fileBuff, String masterFileId, String prefix, String fileExtName);

    byte[] download(String fileId);

    int downloadFile(String fileId, String localFile);

    FileInfo getFileInfo(String fileId);


    Map<String, Object> uploadFileName(byte[] fileBuff, String fileName);
}
