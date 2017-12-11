package com.jokerwan.mvpretrofitrxdemo.api;

import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ${JokerWan} on 2017/9/22.
 * WeChat：wjc398556712
 * Function：线程调度
 */

public class ApiUtils {

    /**
     * 线程切换
     * @return
     */
    public static <T> FlowableTransformer<T, T> getScheduler() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
