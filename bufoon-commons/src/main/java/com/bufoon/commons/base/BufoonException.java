package com.bufoon.commons.base;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/5/5 23:55
 * @Desc: as follows.
 */
public class BufoonException extends Exception {

    private String proName;
    private String code;
    private Object data;
    private String desc;

    public BufoonException() {
        super();
    }

    public BufoonException(String message) {
        super(message);
    }

    public BufoonException(String message, Throwable cause) {
        super(message, cause);
    }

    public BufoonException(Throwable cause) {
        super(cause);
    }

    protected BufoonException(String proName, Object data, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.proName = proName;
        this.data = data;
    }

    public BufoonException(String proName, String code, String message, Object data, Throwable cause) {
        super(message, cause);
        this.proName = proName;
        this.code = code;
        this.data = data;
    }

    public BufoonException(String proName, String code, String message, String desc, Object data, Throwable cause) {
        this(proName, code, message, data, cause);
        this.desc = desc;
    }

    public BufoonException(String proName, String code, String message) {
        this(proName, code, message, null, null);
    }
    public BufoonException(String proName, String code, String message, String desc) {
        this(proName, code, message, null, null);
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
