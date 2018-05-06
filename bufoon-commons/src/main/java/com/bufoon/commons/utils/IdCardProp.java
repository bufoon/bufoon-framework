package com.bufoon.commons.utils;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/2/13 14:22
 * @Desc: as follows.
 */
public class IdCardProp{
    private String idCardNo;
    private Integer sex;
    private String sexCn;
    private String birthday;
    private Integer age;

    public IdCardProp() {
    }

    public IdCardProp(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public IdCardProp(String idCardNo, Integer sex, String sexCn, String birthday, Integer age) {
        this.idCardNo = idCardNo;
        this.sex = sex;
        this.sexCn = sexCn;
        this.birthday = birthday;
        this.age = age;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
        this.setSexCn(sex == 1 ? "男" : "女");
    }

    public String getSexCn() {
        return sexCn;
    }

    public void setSexCn(String sexCn) {
        this.sexCn = sexCn;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
