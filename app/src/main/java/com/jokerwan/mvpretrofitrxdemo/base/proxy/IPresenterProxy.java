package com.jokerwan.mvpretrofitrxdemo.base.proxy;

import com.jokerwan.mvpretrofitrxdemo.base.AWanBasePresenter;
import com.jokerwan.mvpretrofitrxdemo.base.IWanBaseView;
import com.jokerwan.mvpretrofitrxdemo.base.factory.IWanPresenterFactory;

/**
 * Created by JokerWan on 2017/12/11.
 * WeChat: wjc398556712
 * Function: 定义一个代理接口提供设置工厂、获取工厂、获取Presenter的方法,子类可以不使用
 *           注解创建工厂，可以自己实现创建工厂的方法
 */

public interface IPresenterProxy <V extends IWanBaseView,P extends AWanBasePresenter<V>> {

    /**
     * 设置创建Presenter的工厂
     * @param wanPresenterFactory 类型
     */
    void setPresenterFactory(IWanPresenterFactory<V,P> wanPresenterFactory);

    /**
     * 获取Presenter的工厂类
     * @return IWanPresenterFactory
     */
    IWanPresenterFactory<V,P> getPresenterFactory();

    /**
     * 获取创建的Presenter
     * @return 指定类型的Presenter
     */
    P getPresenter();

}
