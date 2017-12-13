package com.jokerwan.mvpretrofitrxdemo.api;


import com.jokerwan.mvpretrofitrxdemo.model.response.MoiveListResponse;

import io.reactivex.Flowable;
import retrofit2.http.POST;

/**
 * Created by ${JokerWan} on 2017/9/22.
 * WeChat：wjc398556712
 * Function：
 */

public interface WApi {

    //获取电影列表
    @POST("/PageSubArea/TrailerList.api")
    Flowable<MoiveListResponse> getMoiveList();

}

