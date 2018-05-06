package com.bufoon.commons.utils;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


/** 数据解压缩
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/3/21 14:15
 * @Desc: as follows.
 */
public class GzipUtil
{
    private static final Logger LOG = LoggerFactory.getLogger(GzipUtil.class);

    /**
     * 压缩数据
     *
     * @param str
     * @return
     * @throws IOException
     */
    public static String compress(String str)
    {
        try
        {
            if (str == null || str.length() == 0)
            {
                return str;
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes());
            gzip.close();
            return out.toString("ISO-8859-1");
        } catch (IOException e)
        {
            LOG.error("压缩数据失败!数据:" + str);
        }
        return null;
    }

    /**
     * 解压缩数据
     *
     * @param str
     * @param charsetName
     * @return
     * @throws IOException
     */
    public static String uncompress(String str, String charsetName)
    {
        ByteArrayInputStream in = null;
        GZIPInputStream gunzip = null;
        try
        {
            if (str == null || str.length() == 0)
            {
                return str;
            }
            if(null == charsetName)
                charsetName = "utf-8";
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
            gunzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = gunzip.read(buffer)) >= 0)
            {
                out.write(buffer, 0, n);
            }
            out.flush();
            return out.toString(charsetName);
        } catch (IOException e)
        {
            LOG.error("解压缩数据失败!数据:" + str);
        } finally
        {
            try
            {
                if (null != in)
                    in.close();
                if (null != gunzip)
                    gunzip.close();
            } catch (IOException e)
            {
                LOG.error(ExceptionUtils.getStackTrace(e));
            }
        }
        return null;
    }

}
