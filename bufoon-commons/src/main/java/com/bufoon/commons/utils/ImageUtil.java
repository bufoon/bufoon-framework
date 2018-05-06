package com.bufoon.commons.utils;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/2/28 10:57
 * @Desc: as follows.
 */
public class ImageUtil {

    /**
     * 根据二进制流判断图片扩展类型
     * @param data
     * @return
     */
    public static String getType(byte[] data) {
        String type = "JPG";
        if (data[1] == 'P' && data[2] == 'N' && data[3] == 'G') {
            type = "PNG";
            return type;
        }
        if (data[0] == 'G' && data[1] == 'I' && data[2] == 'F') {
            type = "GIF";
            return type;
        }
        if (data[6] == 'J' && data[7] == 'F' && data[8] == 'I'
                && data[9] == 'F') {
            type = "JPG";
            return type;
        }
        return type;
    }
}
