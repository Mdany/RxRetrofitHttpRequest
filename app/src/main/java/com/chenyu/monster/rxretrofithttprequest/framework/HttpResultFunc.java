package com.chenyu.monster.rxretrofithttprequest.framework;

import rx.functions.Func1;

/**
 * Created by chenyu on 16/4/26.
 * 统一处理Http的ResultCode，并将data分离出来返回给subscriber
 * {
 * "resultCode": 0,
 * "resultMessage": "成功",
 * "data": {}
 * }
 *
 * T 真正需要的数据类型
 */
public class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
    @Override
    public T call(HttpResult<T> tHttpResult) {
        if (tHttpResult.getCount() == 0) {
            throw new ApiException(100);
        }
        return tHttpResult.getSubjects();
    }
}
