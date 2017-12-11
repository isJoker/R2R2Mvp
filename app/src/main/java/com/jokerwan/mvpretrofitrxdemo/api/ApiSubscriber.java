package com.jokerwan.mvpretrofitrxdemo.api;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by ${JokerWan} on 2017/9/22.
 * WeChat：wjc398556712
 * Function：过滤onComplete()
 */

public abstract class ApiSubscriber<T> extends ResourceSubscriber<T> {

    @Override
    public void onComplete() {

    }

}
