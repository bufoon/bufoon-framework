package com.bufoon.commons.utils;

import java.text.ParseException;
import java.util.Date;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/1/31 11:42
 * @Desc: as follows.
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {


    /**
     * 两个日期之差， 单位毫秒
     * @param start
     * @param end
     * @return
     */
    public static long diffDate(Date start, Date end){
        if (start == null || end == null){
            return -1;
        }
        return end.getTime()-start.getTime();
    }

    /**
     * 两个日期之差， 单位毫秒
     * @param startDate
     * @param endDate
     * @param format
     * @return
     */
    public static long diffDate(String startDate, String endDate, String format){
        try {
            Date start = parseDate(startDate, format);
            Date end = parseDate(endDate, format);
            return diffDate(start, end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(8&127);
        int a= 10;
        //十进制 -> 二进制
        String str = Integer.toBinaryString(a);
        while(str.length() < 32){
            str = 0 + str;
        }
        System.out.println(str);

        //二进制 -> 十进制

        str = "1111111"; //补0
        System.out.println(Integer.parseInt(str, 2));

    }
}
