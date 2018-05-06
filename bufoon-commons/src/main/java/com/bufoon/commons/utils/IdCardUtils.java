package com.bufoon.commons.utils;

import com.alibaba.fastjson.JSON;

import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/2/7 16:49
 * @Desc: as follows.
 */
public class IdCardUtils {


    /**
     * 解析身份证，返回年龄，性别，生日
     * @param certificateNo
     * @return
     */
    public static IdCardProp parseCertificateNo(String certificateNo) {

        IdCardProp resultDTO = null;
        String myRegExpIDCardNo = "^\\d{6}(((19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])\\d{3}([0-9]|x|X))|(\\d{2}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])\\d{3}))$";
        boolean valid= Pattern.matches(myRegExpIDCardNo,certificateNo)||(certificateNo.length() == 17 && Pattern.matches(myRegExpIDCardNo,certificateNo.substring(0,15)));
        if(!valid){
            return resultDTO;
        }
        resultDTO = new IdCardProp(certificateNo);
        int idxSexStart = 16;
        int birthYearSpan = 4;
        //如果是15位的证件号码
        if(certificateNo.length() == 15) {
            idxSexStart = 14;
            birthYearSpan = 2;
        }

        //性别
        String idxSexStr = certificateNo.substring(idxSexStart, idxSexStart + 1);
        int idxSex = Integer.parseInt(idxSexStr) % 2;
        resultDTO.setSex(idxSex);

        //出生日期
        String year = (birthYearSpan == 2 ? "19" : "") + certificateNo.substring(6, 6 + birthYearSpan);
        String month = certificateNo.substring(6 + birthYearSpan, 6 + birthYearSpan + 2);
        String day = certificateNo.substring(8 + birthYearSpan, 8 + birthYearSpan + 2);
        String birthday = year + '-' + month + '-' + day;
        resultDTO.setBirthday(birthday);

        //年龄
        Calendar certificateCal = Calendar.getInstance();
        Calendar currentTimeCal = Calendar.getInstance();
        certificateCal.set(Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(day));
        int yearAge = (currentTimeCal.get(currentTimeCal.YEAR)) - (certificateCal.get(certificateCal.YEAR));
        certificateCal.set(currentTimeCal.get(Calendar.YEAR), Integer.parseInt(month)-1, Integer.parseInt(day));
        int monthFloor = (currentTimeCal.before(certificateCal) ? 1 : 0);
        resultDTO.setAge(yearAge - monthFloor);

        return resultDTO;
    }

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(IdCardUtils.parseCertificateNo("360730199110291136")));
    }
}
