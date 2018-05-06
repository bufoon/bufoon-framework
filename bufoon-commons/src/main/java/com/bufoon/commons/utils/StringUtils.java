package com.bufoon.commons.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.springframework.util.StringUtils {
    public static boolean isNotEmpty(Object str){
        return !isEmpty(str);
    }

    public static Map<String, String> parseAddress(String address){
        String regex="(?<province>[^省]+省|.+自治区)(?<city>[^市]+市|.+自治州)(?<county>[^县]+县|.+区)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
        Matcher m=Pattern.compile(regex).matcher(address);
        String province=null,city=null,county=null,town=null,village=null;
        Map<String,String> row=null;
        System.out.println("====================================");
        while(m.find()){
            row=new LinkedHashMap<String,String>();
            province=m.group("province");
            row.put("province", province==null?"":province.trim());
            city=m.group("city");
            row.put("city", city==null?"":city.trim());
            county=m.group("county");
            row.put("county", county==null?"":county.trim());
            town=m.group("town");
            row.put("town", town==null?"":town.trim());
            village=m.group("village");
            row.put("village", village==null?"":village.trim());
        }
        System.out.println("====================================");
        return row;
    }


    public static void main(String[] args) {
        System.out.println(StringUtils.parseAddress("北京市通州区大梁镇"));
    }
}
