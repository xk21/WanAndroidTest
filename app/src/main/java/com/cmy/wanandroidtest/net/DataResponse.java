package com.cmy.wanandroidtest.net;

/**
 * Created by Linsa on 2018/4/3:10:00.
 * des:返回数据的基础bean
 */

public class DataResponse<T> {

    public T data;
    private int errorCode;
    private String errorMsg;
    private int error;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
