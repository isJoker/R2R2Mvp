package com.jokerwan.mvpretrofitrxdemo.api;

/**
 * Created by JokerWan on 2017/12/11.
 * WeChat: wjc398556712
 * Function: Retrofit单例工厂
 */
public class ApiFactory {

    protected static final Object monitor = new Object();
    static WApi wApi = null;

    public static WApi getwApi() {
        synchronized (monitor) {
            if (wApi == null) {
                wApi = new ApiRetrofit().getwApi();
            }
            return wApi;
        }
    }

}
