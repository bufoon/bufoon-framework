package com.bufoon.storage.fdfs;

import com.bufoon.commons.base.BufoonException;
import com.bufoon.commons.utils.FileTypeUtil;
import com.bufoon.commons.utils.FileUtil;
import com.bufoon.storage.fdfs.common.NameValuePair;
import com.bufoon.storage.fdfs.fastdfs.FileInfo;
import com.bufoon.storage.fdfs.fastdfs.StorageClient;
import com.bufoon.storage.fdfs.fastdfs.StorageClient1;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/2/27 10:20
 * @Desc: as follows.
 */
@Service
public class FastdfsServiceImp implements IFastdfsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FastdfsPool fastdfsPool;

    @Override
    public String uploadFile(byte[] fileBuff, String fileExtName, NameValuePair[] metaList) {
        StorageClient1 storageClient = null;
        try {
            storageClient = fastdfsPool.acquire();
            String fileId = storageClient.upload_file1(fileBuff, fileExtName, metaList);
            if (StringUtils.isEmpty(fileId)) {
                logger.error("file upload fail");
                throw new BufoonException("file upload fail");
            }
            return fileId;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("file upload fail");
            return null;
        }finally{
            fastdfsPool.release(storageClient);
        }
    }

    @Override
    public String uploadFile(byte[] fileBuff, String fileExtName) {
        return uploadFile(fileBuff, fileExtName, null);
    }


    @Override
    public String uploadFile(String filePath, String fileExtName){
        return uploadFile(filePath, fileExtName, null);
    }

    @Override
    public String uploadFile(String filePath, String fileExtName, NameValuePair[] metaList){
        StorageClient1 storageClient = null;
        try {
            storageClient = fastdfsPool.acquire();
            String fileId = storageClient.upload_file1(filePath, fileExtName, metaList);
            if (fileId == null) {
                throw new BufoonException("upload file fail");
            }
            return fileId;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally{
            fastdfsPool.release(storageClient);
        }
    }

    @Override
    public String uploadFile(String masterFileId, String prefix, String path, String fileExtName, NameValuePair[] metaList){
        StorageClient1 storageClient = null;
        try {
            storageClient = fastdfsPool.acquire();
            String fileId = storageClient.upload_file1(masterFileId, prefix, path, fileExtName, metaList);
            if (fileId == null) { //先判断slave文件是否是已经上传，如果是已经上传则正常返回
                FileInfo fileInfo = storageClient.query_file_info1(getSlaveFileId(masterFileId, "_small"));
                if (fileInfo == null) {
                    logger.error("upload slave file fail, fileid="+masterFileId);
                    throw new BufoonException("upload file fail");
                } else {
                    logger.info("file["+masterFileId+"] 文件的slave文件已存在，不需要重复上传");
                    fileId= getSlaveFileId(masterFileId, "_small");
                }
            }
            return fileId;
        } catch (Exception e) {
            logger.error("upload slave file exception e =" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally{
           fastdfsPool.release(storageClient);
        }
    }

    @Override
    public byte[] download(String fileId){
        StorageClient1 storageClient = null;
        try {
            storageClient = fastdfsPool.acquire();
            return storageClient.download_file1(fileId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally{
            fastdfsPool.release(storageClient);
        }
    }

    @Override
    public int downloadFile(String fileId, String localFile){
        StorageClient1 storageClient = null;
        try {
            storageClient = fastdfsPool.acquire();
            return storageClient.download_file1(fileId, localFile);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally{
            fastdfsPool.release(storageClient);
        }
    }

    @Override
    public FileInfo getFileInfo(String fileId){
        StorageClient1 storageClient = null;
        try {
            storageClient = fastdfsPool.acquire();
            return storageClient.get_file_info1(fileId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            fastdfsPool.release(storageClient);
        }
        return null;
    }

    private void closeClient(StorageClient storageClient) {
        if (storageClient != null) {
            try {
                storageClient.getTrackerServer().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getSlaveFileId(String fileId, String suffix) {
        StringBuilder sb = new StringBuilder(fileId);
        sb.insert(fileId.lastIndexOf("."), suffix);
        return sb.toString();
    }


    /**
     * 字节流方式上传
     */
    @Override
    public  Map<String, Object> uploadFileName(byte[] fileBuff, String fileName) {
        logger.debug("fileName:" + fileName);
        String originalFileName = FilenameUtils.getName(fileName);// 文件名
        logger.debug("originalFileName:" + originalFileName);
        String baseName = FilenameUtils.getBaseName(fileName);// 不含后缀名
        logger.debug("baseName:" + baseName);
        String fileExtName = FilenameUtils.getExtension(originalFileName);// 文件后缀名
        logger.debug("fileExtName:" + fileExtName);

        long length = fileBuff.length;// 字节
        logger.debug("length:" + length);

        logger.debug("fileBuff.length:" + fileBuff.length);

        boolean isImage = FileTypeUtil.isImage(originalFileName);
        logger.debug("isImage:" + isImage);
        String mimeType = FileUtil.getMimeType(fileName);
        logger.debug("mimeType:" + mimeType);

        int width = 0;
        int height = 0;
        if (isImage) {
            int[] imageInfo = FileTypeUtil.getImageInfo(fileBuff);
            if (imageInfo != null) {
                width = imageInfo[0];
                height = imageInfo[1];
            }
        }

        String fileType = FileTypeUtil.getFileTypeByStream(fileBuff);
        logger.debug("fileType:" + fileType);

        NameValuePair[] metaList = new NameValuePair[] { new NameValuePair("fileName", fileName), new NameValuePair("isImage", isImage + ""), new NameValuePair("mimeType", mimeType), new NameValuePair("width", width + ""), new NameValuePair("height", height + ""), new NameValuePair("author", "Fastdfs") };

        boolean status = false;
        String message = "文件上传失败！";
        String[] responseData = storeFile(fileBuff, fileExtName, metaList);
        Map<String, Object> uploadResult = new HashMap<String, Object>();
        if (responseData != null) {
            status = true;
            message = "文件上传成功！";

            uploadResult.put("isImage", isImage);
            if (isImage) {
                uploadResult.put("width", width);
                uploadResult.put("height", height);
            }

            uploadResult.put("groupName", responseData[0]);
            uploadResult.put("storageFileName", responseData[1]);
            uploadResult.put("link", responseData[0] + "/" + responseData[1]);// 文件访问链接
        }

        uploadResult.put("status", status);
        uploadResult.put("message", message);

        uploadResult.put("fileName", fileName);
        uploadResult.put("mimeType", mimeType);
        uploadResult.put("length", length);

        return uploadResult;
    }



    private  String[] storeFile(byte[] fileBuff, String fileExtName, NameValuePair[] metaList) {
        String[] responseData = null;
        StorageClient1 storageClient = null;
        try {
            storageClient = fastdfsPool.acquire();
            responseData = storageClient.upload_file(fileBuff, fileExtName.toLowerCase(), metaList);
        } catch (Exception e) {
            logger.error("storeFile时发生异常:", e);
        } finally {
            fastdfsPool.release(storageClient);
        }
        return responseData;
    }

    @Override
    public String uploadFile(byte[] fileBuff, String masterFileId, String prefix, String fileExtName) {
        StorageClient1 storageClient = null;
        try {
            storageClient = fastdfsPool.acquire();
            String fileId = storageClient.upload_file1(masterFileId, prefix, fileBuff, fileExtName, null);
            if (fileId == null) { //先判断slave文件是否是已经上传，如果是已经上传则正常返回
                FileInfo fileInfo = storageClient.query_file_info1(getSlaveFileId(masterFileId, "prefix"));
                if (fileInfo == null) {
                    logger.error("upload slave file fail, fileid="+masterFileId);
                    throw new BufoonException("upload file fail");
                } else {
                    logger.info("file["+masterFileId+"] 文件的slave文件已存在，不需要重复上传");
                    fileId= getSlaveFileId(masterFileId, "_small");
                }
            }
            return fileId;
        } catch (Exception e) {
            logger.error("upload slave file exception e =" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally{
            fastdfsPool.release(storageClient);
        }
    }
}
