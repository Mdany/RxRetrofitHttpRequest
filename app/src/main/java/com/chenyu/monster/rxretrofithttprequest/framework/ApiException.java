package com.chenyu.monster.rxretrofithttprequest.framework;

/**
 * Created by chenyu on 16/4/26.
 */
public class ApiException extends RuntimeException {
    public static final int USER_NOT_EXIST = 100;
    public static final int WRONG_PASSWORD = 101;

    public ApiException(int resultCode) {
        super(getApiExceptionMessage(resultCode));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    private static String getApiExceptionMessage(int resultCode) {
        switch (resultCode) {
            case USER_NOT_EXIST:
                return "用户不存在";
            case WRONG_PASSWORD:
                return "密码错误";
            default:
                return "未知错误";
        }
    }
}
