package com.jokerwan.mvpretrofitrxdemo.view;

import com.jokerwan.mvpretrofitrxdemo.base.IWanBaseView;
import com.jokerwan.mvpretrofitrxdemo.model.MoiveListResponse;

/**
 * Created by JokerWan on 2017/12/11.
 * WeChat: wjc398556712
 * Function:
 */

public interface IMoiveListView extends IWanBaseView{

    void onLoading();

    void onLoadSucess(MoiveListResponse moiveListResponse);

    void onLoadFail(String msg);
}
